package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.PorteInterface;
import fr.epsi.controleacces.utilities.ZoneInterface;

public class Zone implements ZoneInterface {
    private final PorteInterface[] _portes;
    private String _zone;
    private boolean _bloquée = false;

    public Zone(String zone, PorteInterface... portes) {
        _zone = zone;
        _portes = portes;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }

    @Override
    public String getZone() {
        return _zone;
    }

    public void IntervertirBloquéDébloqué() {
        _bloquée = !_bloquée;
    }

    @Override
    public boolean VerifierSiZoneEstBloquée() {
        return _bloquée;
    }
}
