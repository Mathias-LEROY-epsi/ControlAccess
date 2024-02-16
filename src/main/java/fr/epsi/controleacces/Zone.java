package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.ZoneInterface;

import java.util.List;

public class Zone implements ZoneInterface {

    private List<PorteSpy> _portes;

    @Override
    public void AjouterPortes(List<PorteSpy> portes) {
        _portes = portes;
    }
}
