package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.HorlogeInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

public class PorteFake implements PorteInterface {
    private final HorlogeInterface _horloge;
    public boolean bloquée = false;
    private Integer _heureDébut = 8;
    private Integer _heureFin = 17;

    public PorteFake(HorlogeInterface horloge) {
        _horloge = horloge;
    }

    public void Ouvrir() {
    }

    public void IntervertirBloquéDébloqué() {
        bloquée = !bloquée;
    }

    @Override
    public boolean EstBloquée() {
        return bloquée;
    }

    @Override
    public void DefinirPlageHoraire(Integer heureDébut, Integer heureFin) {
        this._heureDébut = heureDébut;
        this._heureFin = heureFin;
    }

    @Override
    public boolean EstDansPlageHoraire() {
        return _horloge.GetHeureActuelle() >= this._heureDébut && _horloge.GetHeureActuelle() <= this._heureFin;
    }
}
