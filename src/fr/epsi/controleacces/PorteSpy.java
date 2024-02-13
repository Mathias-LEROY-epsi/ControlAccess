package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.PorteInterface;

public class PorteSpy implements PorteInterface {
    private final PorteFake _fake;
    private Integer _nombreOuvertureDemandée = 0;

    public PorteSpy(PorteFake fake) {
        _fake = fake;
    }

    public Integer VérifierOuvertureDemandée() {
        return _nombreOuvertureDemandée;
    }

    @Override
    public void Ouvrir() {
        _nombreOuvertureDemandée += 1;
        _fake.Ouvrir();
    }

    @Override
    public void IntervertirBloquéDébloqué() {
        _fake.IntervertirBloquéDébloqué();
    }

    @Override
    public boolean EstBloquée() {
        return _fake.bloquée;
    }

    @Override
    public void DefinirPlageHoraire(Integer heureDébut, Integer heureFin) {
        _fake.DefinirPlageHoraire(heureDébut, heureFin);
    }

    @Override
    public boolean EstDansPlageHoraire() {
        return _fake.EstDansPlageHoraire();
    }
}
