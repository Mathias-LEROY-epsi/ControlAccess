package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.PorteInterface;

public class PorteSpy implements PorteInterface {
    public PorteSpy(){
    }

    private Integer _nombreOuvertureDemandée = 0;

    public Integer VérifierOuvertureDemandée() {
        return _nombreOuvertureDemandée;
    }

    @Override
    public void Ouvrir() {
        _nombreOuvertureDemandée += 1;
    }
}
