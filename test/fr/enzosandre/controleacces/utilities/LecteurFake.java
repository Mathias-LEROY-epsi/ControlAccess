package fr.enzosandre.controleacces.utilities;

import fr.enzosandre.controleacces.LecteurInterface;
import fr.enzosandre.controleacces.PorteInterface;

public class LecteurFake implements LecteurInterface {
    private final PorteInterface[] _portes;

    public void SimulerDétectionBadge() {
        _aDétectéBadge = true;
    }

    private boolean _aDétectéBadge = false;

    @Override
    public boolean ADétectéBadge() {
        return _aDétectéBadge;
    }

    public LecteurFake(PorteInterface... portesLiées) {
        this._portes = portesLiées;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }
}
