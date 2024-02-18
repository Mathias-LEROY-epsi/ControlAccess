package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            boolean aDetecteBadge = lecteur.aDétectéBadge();
            boolean jourBloqué = lecteur.VérifierSiJourActuelEstBloqué();
            boolean estAdministrateur = lecteur.VérifierSiBagdeEstAdministrateur();
            boolean estTechnicien = lecteur.VérifierSiBagdeEstTechnicien();
            boolean estUtilisateur = lecteur.VérifierSiBagdeEstUtilisateur();
            boolean estVisiteur = false;

            BadgeInterface badge = lecteur.getBadge();
            String badgeZone;

            if (badge == null) {
                badgeZone = "A";
                estVisiteur = true;
            } else {
                badgeZone = badge.getZone();
            }

            for (var porte : lecteur.getPortes()) {
                if (estVisiteur) {
                    continue;
                }
                if (porte.EstEnMaintenance() && estTechnicien) {
                    porte.Ouvrir();
                }
                if (!porte.EstEnMaintenance()) {
                    if (estTechnicien && porte.EstUnAccèsRéservéAuxTechniciens()) {
                        porte.Ouvrir();
                    }
                    if (!estTechnicien && !porte.EstUnAccèsRéservéAuxTechniciens()) {
                        if (estAdministrateur) {
                            porte.Ouvrir();
                        } else if (estUtilisateur && lecteur.peutOuvrir(badgeZone, porte.getZone())) {
                            if ((Boolean.FALSE.equals(jourBloqué) && Boolean.FALSE.equals(porte.VerifierSiPorteBloquée()))) {
                                if (porte.EstDansPlageHoraire() || aDetecteBadge && !lecteur.badgeBloqué()) {
                                    porte.Ouvrir();
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
