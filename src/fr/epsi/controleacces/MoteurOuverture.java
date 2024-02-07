package fr.epsi.controleacces;

public class MoteurOuverture {
    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for(var lecteur : lecteurs)
            if(lecteur.ADétectéBadge())
                for(var porte : lecteur.getPortes())
                    porte.Ouvrir();
    }
}
