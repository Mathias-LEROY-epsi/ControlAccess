package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs) {
            boolean aDetecteBadge = lecteur.aDétectéBadge();
            boolean jourBloqué = lecteur.VérifierSiJourActuelEstBloqué();
            for (var porte : lecteur.getPortes()) {
                if (!jourBloqué && porte.EstDansPlageHoraire() && !porte.EstBloquée())
                    porte.Ouvrir();
                else {
                    if (!jourBloqué && aDetecteBadge && !lecteur.badgeBloqué() && !porte.EstBloquée()) {
                        porte.Ouvrir();
                    }
                }
            }
        }
    }
}
