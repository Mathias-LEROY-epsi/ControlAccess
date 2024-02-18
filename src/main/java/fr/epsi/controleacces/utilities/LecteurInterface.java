package fr.epsi.controleacces.utilities;

public interface LecteurInterface {
    boolean VérifierSiBagdeEstAdministrateur();

    boolean VérifierSiBagdeEstTechnicien();

    boolean VérifierSiBagdeEstUtilisateur();

    boolean VérifierSiBagdeEstVisiteur();

    boolean aDétectéBadge();

    boolean badgeBloqué();

    ZoneInterface[] getZones();

    boolean VérifierSiJourActuelEstBloqué();

    boolean peutOuvrir(String badgeZone, String zone);

    BadgeInterface getBadge();
}
