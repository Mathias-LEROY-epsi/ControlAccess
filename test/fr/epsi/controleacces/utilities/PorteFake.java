package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.PorteInterface;

public class PorteFake implements PorteInterface {

    public boolean estBloquée;
    public PorteFake(boolean _bloquée){
        estBloquée = _bloquée;
    }

    public void Ouvrir() {
    }

    public boolean EstBloquée() {
        return estBloquée;
    }
}
