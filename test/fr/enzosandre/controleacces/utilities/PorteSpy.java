package fr.enzosandre.controleacces.utilities;

import fr.enzosandre.controleacces.LecteurInterface;
import fr.enzosandre.controleacces.PorteInterface;
import jdk.jshell.spi.ExecutionControl;

public class PorteSpy implements PorteInterface {
    public PorteSpy(LecteurInterface lecteur){
    }

    private boolean _ouvertureDemandée = false;

    public boolean VérifierOuvertureDemandée() {
        return _ouvertureDemandée;
    }

    @Override
    public void Ouvrir() {
        _ouvertureDemandée = true;
    }
}
