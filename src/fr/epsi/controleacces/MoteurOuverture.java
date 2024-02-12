package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.HorlogeInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs)
            if(lecteur.aDétectéBadge()) // stocker la valeur
                for (var porte : lecteur.getPortes())
                    if(porte.EstDansPlageHoraire())
                        porte.Ouvrir();
                    else if(!lecteur.badgeBloqué() && !porte.EstBloquée()) {
                            porte.Ouvrir();
                    }
    }
}
