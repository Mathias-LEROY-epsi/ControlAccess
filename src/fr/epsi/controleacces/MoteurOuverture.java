package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            boolean aDetecteBadge = lecteur.aDétectéBadge();
            boolean jourBloqué = lecteur.VérifierSiJourActuelEstBloqué();
            boolean estAdministrateur = lecteur.VérifierSiBagdeEstAdministrateur();

            for (var porte : lecteur.getPortes()) {
                if (porte.EstEnMaintenance()) {
                    continue;
                }
                if (estAdministrateur)
                    porte.Ouvrir();
                else if (Boolean.FALSE.equals(jourBloqué) && porte.EstDansPlageHoraire() && Boolean.FALSE.equals(porte.EstBloquée()))
                    porte.Ouvrir();
                else {
                    if (Boolean.FALSE.equals(jourBloqué) && aDetecteBadge && Boolean.FALSE.equals(lecteur.badgeBloqué()) && Boolean.FALSE.equals(porte.EstBloquée())) {
                        porte.Ouvrir();
                    }
                }

            }
        }
    }
}
