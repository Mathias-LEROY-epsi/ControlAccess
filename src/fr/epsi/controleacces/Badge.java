package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

public class Badge implements BadgeInterface {
    public boolean estBloqué = false;

    public Badge() {}

    @Override
    public void IntervertirBloquéDébloqué() {
        estBloqué = !estBloqué;
    }

    @Override
    public boolean EstBloqué() {
        return estBloqué;
    }
}
