package fr.epsi.controleacces.utilities;

public interface ZoneInterface {
    PorteInterface[] getPortes();

    String getZone();

    boolean VerifierSiZoneEstBloquée();
}
