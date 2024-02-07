package fr.enzosandre.controleacces.utilities;

import fr.enzosandre.controleacces.LecteurInterface;

public class LecteurFake implements LecteurInterface {
    public void SimulerDétectionBadge() {
        _aDétectéBadge = true;
    }

    private boolean _aDétectéBadge = false;

    @Override
    public boolean ADétectéBadge() {
        return _aDétectéBadge;
    }
}
