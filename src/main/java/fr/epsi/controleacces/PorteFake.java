package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.HorlogeInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

import java.util.List;

public class PorteFake implements PorteInterface {
    private final HorlogeInterface _horloge;
    private boolean _bloquée = false;
    private boolean _accèsRéservéAuxTechniciens = false;
    private boolean _accèsRéservéAuxAdmins = false;
    private List<Integer> _plageHoraire = List.of(8, 17);
    private List<Integer> _heureFermeture = List.of(23, 24);

    public PorteFake(HorlogeInterface horloge) {
        _horloge = horloge;
    }

    public void Ouvrir() {
    }

    public void IntervertirBloquéDébloqué() {
        _bloquée = !_bloquée;
    }

    @Override
    public boolean EstBloquée() {
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

    public void AccèsRéservéAuxTechniciens() {
        _accèsRéservéAuxTechniciens = true;
    }

    @Override
    public boolean EstUnAccèsRéservéAuxTechniciens() {
        return _accèsRéservéAuxTechniciens;
    }

    public void AccèsRéservéAuxAdmins() {
        _accèsRéservéAuxAdmins = true;
    }

    @Override
    public boolean EstUnAccèsRéservéAuxAdmins() {
        return _accèsRéservéAuxAdmins;
    }
}
