package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.LecteurInterface;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for(var lecteur : lecteurs)
            if(lecteur.aDétectéBadge() && !lecteur.badgeBloqué())
                for(var porte : lecteur.getPortes())
                    if(!porte.EstBloquée())
                        porte.Ouvrir();
    }
}
