package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.LecteurInterface;
import fr.epsi.controleacces.PorteInterface;

public class lecteurFake implements LecteurInterface {
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

    public lecteurFake(PorteInterface... portesLiées) {
        this._portes = portesLiées;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }
}
