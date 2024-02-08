package fr.epsi.controleacces;

public interface LecteurInterface {
    boolean aDétectéBadge();
    boolean badgeBloqué();

    PorteInterface[] getPortes();
}
