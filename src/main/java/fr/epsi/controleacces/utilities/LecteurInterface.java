package fr.epsi.controleacces.utilities;

import java.util.List;

public interface LecteurInterface {
    boolean VérifierSiBagdeEstAdministrateur();

    boolean VérifierSiBagdeEstTechnicien();

    boolean VérifierSiBagdeEstUtilisateur();

    boolean VérifierSiBagdeEstVisiteur();

    boolean aDétectéBadge();

    boolean badgeBloqué();

    ZoneInterface[] getZones();

    boolean VérifierSiJourActuelEstBloqué();

    boolean peutOuvrir(List<String> badgeZone, String zone);

    BadgeInterface getBadge();
}
