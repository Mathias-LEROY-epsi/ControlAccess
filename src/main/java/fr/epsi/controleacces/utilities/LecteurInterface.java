package fr.epsi.controleacces.utilities;

public interface LecteurInterface {
    boolean VérifierSiBagdeEstAdministrateur();

    boolean aDétectéBadge();

    boolean badgeBloqué();

    PorteInterface[] getPortes();

    boolean VérifierSiJourActuelEstBloqué();
}
