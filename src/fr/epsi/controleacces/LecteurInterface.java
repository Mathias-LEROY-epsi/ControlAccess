package fr.epsi.controleacces;

public interface LecteurInterface {
    boolean ADétectéBadge();
    boolean BadgeBloqué();

    PorteInterface[] getPortes();
}
