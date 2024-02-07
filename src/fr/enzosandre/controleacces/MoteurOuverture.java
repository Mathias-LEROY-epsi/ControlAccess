package fr.enzosandre.controleacces;

public class MoteurOuverture {
    private final PorteInterface[] _porte;

    public MoteurOuverture(PorteInterface... porte) {
        this._porte = porte;
    }

    public void InterrogerLecteur(LecteurInterface lecteur) {
        if(lecteur.ADétectéBadge()) {
            for(var porte : _porte)
                porte.Ouvrir();
        }
    }
}
