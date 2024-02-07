package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.LecteurInterface;
import fr.epsi.controleacces.PorteInterface;

public class LecteurFake implements LecteurInterface {
    private final PorteInterface[] _portes;
    private boolean _aDétectéBadge = false;
    public void SimulerDétectionBadge() {
        _aDétectéBadge = true;
    }
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
