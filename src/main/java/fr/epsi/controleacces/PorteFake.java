package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.HorlogeInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

import java.util.List;

public class PorteFake implements PorteInterface {
    private final HorlogeInterface _horloge;
    private boolean _bloquée = false;
    private List<Integer> _plageHoraire = List.of(8, 17);
    private List<Integer> _heureFermeture = List.of(23, 24);
    private String _zone = "A";

    public PorteFake(HorlogeInterface horloge) {
        _horloge = horloge;
    }

    public void Ouvrir() {
    }

    public void AssocierZone(String zone) {
        _zone = zone;
    }

    @Override
    public String getZone() {
        return _zone;
    }

    public void IntervertirBloquéDébloqué() {
        _bloquée = !_bloquée;
    }

    @Override
    public boolean VerifierSiPorteBloquée() {
        return _bloquée;
    }

    @Override
    public void DefinirPlageHoraire(Integer _heureDébut, Integer _heureFin) {
        _plageHoraire = List.of(_heureDébut, _heureFin);
    }

    @Override
    public void DefinirFermetureMaintenance(Integer _heureDébut, Integer _heureFin) {
        _heureFermeture = List.of(_heureDébut, _heureFin);
    }

    @Override
    public boolean EstEnMaintenance() {
        return _horloge.GetHeureActuelle() >= _heureFermeture.getFirst() && _horloge.GetHeureActuelle() <= _heureFermeture.getLast();
    }

    @Override
    public boolean EstDansPlageHoraire() {
        return _horloge.GetHeureActuelle() >= _plageHoraire.getFirst() && _horloge.GetHeureActuelle() <= _plageHoraire.getLast();
    }
}
