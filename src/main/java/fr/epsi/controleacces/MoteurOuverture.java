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
                if (estAdministrateur && !porte.EstEnMaintenance()) {
                    porte.Ouvrir();
                } else if (lecteur.peutOuvrir(badgeZone, porte.getZone())) {
                    if (porte.EstEnMaintenance()) {
                        continue;
                    }
                    if (!jourBloqué && porte.EstDansPlageHoraire() && !porte.EstBloquée()) {
                        porte.Ouvrir();
                    } else {
                        if (!jourBloqué && aDetecteBadge && !lecteur.badgeBloqué() && !porte.EstBloquée()) {
                            porte.Ouvrir();
                        }
                    }
                }
            }
        }
    }
}
