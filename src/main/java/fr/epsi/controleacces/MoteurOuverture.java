package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;

import java.util.List;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            boolean aDetecteBadge = lecteur.aDétectéBadge();
            boolean jourBloqué = lecteur.VérifierSiJourActuelEstBloqué();
            boolean estAdministrateur = lecteur.VérifierSiBagdeEstAdministrateur();
            boolean estTechnicien = lecteur.VérifierSiBagdeEstTechnicien();
            boolean estUtilisateur = lecteur.VérifierSiBagdeEstUtilisateur();
            boolean estVisiteur = lecteur.VérifierSiBagdeEstVisiteur();

            BadgeInterface badge = lecteur.getBadge();
            List<String> badgeZone;

            if (badge == null || estVisiteur) {
                badgeZone = List.of("Accueil");
            } else {
                badgeZone = badge.getZones();
            }

            for (var zone : lecteur.getZones()) {
                for (var porte : zone.getPortes()) {
                    if (estVisiteur && lecteur.peutOuvrir(badgeZone, zone.getZone())) {
                        porte.Ouvrir();
                    }
                    if (porte.EstEnMaintenance() && estTechnicien) {
                        porte.Ouvrir();
                    }
                    if (!porte.EstEnMaintenance()) {
                        if (estAdministrateur) {
                            if (!porte.EstUnAccèsRéservéAuxTechniciens()) {
                                porte.Ouvrir();
                            }
                        }
                        if (estTechnicien) {
                            if (porte.EstUnAccèsRéservéAuxTechniciens()
                                    && lecteur.peutOuvrir(badgeZone, zone.getZone())) {
                                porte.Ouvrir();
                            }
                        }
                        if (estUtilisateur) {
                            if (!porte.EstUnAccèsRéservéAuxTechniciens() && !porte.EstUnAccèsRéservéAuxAdmins()
                                    && (lecteur.peutOuvrir(badgeZone, zone.getZone())
                                    && (!jourBloqué && !porte.EstBloquée() && !zone.EstBloquée()
                                    && (porte.EstDansPlageHoraire() || aDetecteBadge && !lecteur.badgeBloqué())))) {
                                porte.Ouvrir();
                            }
                        }
                    }
                }
            }
        }
    }
}
