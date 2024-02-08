package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.LecteurInterface;
import fr.epsi.controleacces.PorteInterface;

public class LecteurFake implements LecteurInterface {
    private final PorteInterface[] _portes;
    private boolean _aDétectéBadge = false;
    private boolean _badgeBloqué = false;
    private boolean _porteBloquée = false;

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

    @Override
    public boolean porteBloquée() {
        return _porteBloquée = true;
    }

    public LecteurFake(PorteInterface... portesLiées) {
        this._portes = portesLiées;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }
}
