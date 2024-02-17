package fr.epsi.controleacces.utilities;

public interface LecteurInterface {
    boolean VérifierSiBagdeEstAdministrateur();

    boolean VérifierSiBagdeEstTechnicien();

    boolean VérifierSiBagdeEstUtilisateur();

    boolean aDétectéBadge();

    boolean badgeBloqué();

    PorteInterface[] getPortes();

    boolean VérifierSiJourActuelEstBloqué();

    boolean peutOuvrir(String badgeZone, String porteZone);

    BadgeInterface getBadge();
}
