package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            boolean aDetecteBadge = lecteur.aDétectéBadge();
            boolean jourBloqué = lecteur.VérifierSiJourActuelEstBloqué();
            boolean estAdministrateur = lecteur.VérifierSiBagdeEstAdministrateur();

            BadgeInterface badge = lecteur.getBadge();
            String badgeZone;

            if (badge == null) {
                badgeZone = "A";
            } else {
                badgeZone = badge.getZone();
            }

            for (var porte : lecteur.getPortes()) {
                if (porte.EstEnMaintenance()) {
                    continue;
                }
                if (estAdministrateur) {
                    porte.Ouvrir();
                } else if (lecteur.peutOuvrir(badgeZone, porte.getZone())) {
                    if ((!jourBloqué && !porte.EstBloquée())) {
                        if (porte.EstDansPlageHoraire() || aDetecteBadge && !lecteur.badgeBloqué()) {
                            porte.Ouvrir();
                        }
                    }
                }
            }
        }
    }
}
