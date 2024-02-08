package fr.epsi.controleacces;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for(var lecteur : lecteurs)
            if(lecteur.aDétectéBadge() && !lecteur.badgeBloqué())
                for(var porte : lecteur.getPortes())
                    if(!porte.EstBloquée())
                        porte.Ouvrir();
    }
}
