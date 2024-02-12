package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.HorlogeInterface;

public class Horloge implements HorlogeInterface {
    private Integer _heureActuelle;

    @Override
    public Integer GetHeureActuelle() {
        return _heureActuelle;
    }

    @Override
    public void DefinirHeureActuelle(Integer heureActuelle) {
        this._heureActuelle = heureActuelle;
    }
}
