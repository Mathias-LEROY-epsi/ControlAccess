package fr.epsi.controleacces;

public interface LecteurInterface {
    boolean ADétectéBadge();
    boolean ValiditeDuBadge();

    PorteInterface[] getPortes();
}
