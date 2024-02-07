package fr.epsi.controleacces;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for(var lecteur : lecteurs)
            if(lecteur.ADétectéBadge() && !lecteur.BadgeBloqué())
                for(var porte : lecteur.getPortes())
                    porte.Ouvrir();
    }
}