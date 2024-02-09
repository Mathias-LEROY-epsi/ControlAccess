package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.LecteurInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

public class LecteurFake implements LecteurInterface {
    private final PorteInterface[] _portes;
    private boolean _aDétectéBadge = false;
    private boolean _badgeBloqué = false;

    public void simulerDétectionBadge(Badge badge) {
        _aDétectéBadge = true;
        _badgeBloqué = badge.estBloqué;
    }

    @Override
    public boolean aDétectéBadge() {
        var val = _aDétectéBadge;
        _aDétectéBadge = false;
        return val;
    }

    @Override
    public boolean badgeBloqué() {
       return _badgeBloqué;
    }

    public LecteurFake(PorteInterface... portesLiées) {
        this._portes = portesLiées;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }
}
