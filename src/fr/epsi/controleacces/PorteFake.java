package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.PorteInterface;

public class PorteFake implements PorteInterface {
    public boolean bloquée = false;
    public PorteFake(){}

    public void Ouvrir() {}

    public void IntervertirBloquéDébloqué() {
        bloquée = !bloquée;
    }

    @Override
    public boolean EstBloquée() {
        return bloquée;
    }
}
