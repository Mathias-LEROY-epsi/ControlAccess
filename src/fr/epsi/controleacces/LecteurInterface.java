package fr.epsi.controleacces;

public interface LecteurInterface {
    boolean aDétectéBadge();
    boolean badgeBloqué();
    boolean porteBloquée();

    PorteInterface[] getPortes();
}
