package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;
import fr.epsi.controleacces.utilities.PorteInterface;
import fr.epsi.controleacces.utilities.ZoneInterface;

import java.util.List;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            BadgeInterface badge = lecteur.getBadge();
            Boolean aBadgé = lecteur.VérifierSiBadgeDétecté();
            List<String> badgeZone = getBadgeZone(lecteur, badge);

            for (var zone : lecteur.getZones()) {
                for (var porte : zone.getPortes()) {
                    if (porte.EstEnMaintenance()) {
                        handleMaintenanceMode(lecteur, porte);
                    } else {
                        handleNormalMode(lecteur, aBadgé, badgeZone, zone, porte);
                    }
                }
            }
        }
    }

    private static List<String> getBadgeZone(LecteurInterface lecteur, BadgeInterface badge) {
        if (badge == null || lecteur.VérifierSiBagdeEstVisiteur()) {
            return List.of("Accueil");
        } else {
            return badge.getZones();
        }
    }

    private static void handleMaintenanceMode(LecteurInterface lecteur, PorteInterface porte) {
        if (lecteur.VérifierSiBagdeEstTechnicien()) {
            porte.Ouvrir();
        }
    }

    private static void handleNormalMode(LecteurInterface lecteur, Boolean aBadgé,
                                         List<String> badgeZone, ZoneInterface zone, PorteInterface porte) {
        if (lecteur.VérifierSiBagdeEstVisiteur()
                && lecteur.peutOuvrir(badgeZone, zone.getZone()) && porte.EstDansPlageHoraire()) {
            porte.Ouvrir();
        }
        if (lecteur.VérifierSiBagdeEstAdministrateur() && !porte.EstUnAccèsRéservéAuxTechniciens()) {
            porte.Ouvrir();
        }
        if (lecteur.VérifierSiBagdeEstTechnicien()
                && porte.EstUnAccèsRéservéAuxTechniciens() && lecteur.peutOuvrir(badgeZone, zone.getZone())) {
            porte.Ouvrir();
        }
        if (lecteur.VérifierSiBagdeEstUtilisateur() && canUserOpenDoor(lecteur, aBadgé, badgeZone, zone, porte)) {
            porte.Ouvrir();
        }
    }

    private static boolean canUserOpenDoor(LecteurInterface lecteur, Boolean aBadgé,
                                           List<String> badgeZone, ZoneInterface zone, PorteInterface porte) {
        return !porte.EstUnAccèsRéservéAuxTechniciens()
                && !porte.EstUnAccèsRéservéAuxAdmins()
                && lecteur.peutOuvrir(badgeZone, zone.getZone())
                && !lecteur.VérifierSiJourActuelEstBloqué()
                && !porte.EstBloquée()
                && !zone.EstBloquée()
                && (porte.EstDansPlageHoraire() || aBadgé && !lecteur.badgeBloqué());
    }
}
