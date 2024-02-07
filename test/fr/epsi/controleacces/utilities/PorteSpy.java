package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.PorteInterface;

public class PorteSpy implements PorteInterface {
    public PorteSpy(){
    }

    private boolean _ouvertureDemandée = false;

    public boolean VérifierOuvertureDemandée() {
        return _ouvertureDemandée;
    }

    @Override
    public void Ouvrir() {
        _ouvertureDemandée = false;
    }
}
