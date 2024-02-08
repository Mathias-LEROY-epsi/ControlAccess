package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.PorteInterface;

public class PorteSpy implements PorteInterface {
    private final PorteFake _comportement;

    public PorteSpy(PorteFake comportement) {
        _comportement = comportement;
    }
    private Integer _nombreOuvertureDemandée = 0;

    public Integer VérifierOuvertureDemandée() {
        return _nombreOuvertureDemandée;
    }

    @Override
    public void Ouvrir() {
        _nombreOuvertureDemandée += 1;
        _comportement.Ouvrir();
    }

    @Override
    public boolean EstBloquée() {
        return _comportement.EstBloquée();
    }
}
