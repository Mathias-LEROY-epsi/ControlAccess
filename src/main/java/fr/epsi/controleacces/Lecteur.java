package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.CalendrierInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;
import fr.epsi.controleacces.utilities.ZoneInterface;

import java.util.List;

public class Lecteur implements LecteurInterface {
    private final ZoneInterface[] _zones;
    private final BadgeInterface _badge;
    private final CalendrierInterface _calendrier;
    private boolean _estAdministrateur = false;
    private boolean _estTechnicien = false;
    private boolean _estUtilisateur = false;
    private boolean _estVisiteur = false;
    private boolean _aDétectéBadge = false;
    private boolean _badgeBloqué = false;

    public void simulerDétectionBadge(Badge badge) {
        _aDétectéBadge = true;
        _badgeBloqué = badge.EstBloqué();
    }

    public void VerifierLeGradeDuBadge(Badge badge) {
        if (badge.ObtenirGrade().equals("Admin")) {
            _estAdministrateur = true;
        } else if (badge.ObtenirGrade().equals("Technicien")) {
            _estTechnicien = true;
        } else if (badge.ObtenirGrade().equals("Utilisateur")) {
            _estUtilisateur = true;
        }
    }

    public void DéfinirCommeVisiteur() {
        _estVisiteur = true;
    }

    @Override
    public boolean VérifierSiBagdeEstAdministrateur() {
        return _estAdministrateur;
    }

    @Override
    public boolean VérifierSiBagdeEstTechnicien() {
        return _estTechnicien;
    }

    @Override
    public boolean VérifierSiBagdeEstUtilisateur() {
        return _estUtilisateur;
    }

    @Override
    public boolean VérifierSiBagdeEstVisiteur() {
        return _estVisiteur;
    }

    @Override
    public boolean VérifierSiBadgeDétecté() {
        var val = _aDétectéBadge;
        _aDétectéBadge = false;
        return val;
    }

    @Override
    public boolean badgeBloqué() {
        return _badgeBloqué;
    }

    public Lecteur(BadgeInterface badge, CalendrierInterface calendrier, ZoneInterface... zoneLiées) {
        _badge = badge;
        _calendrier = calendrier;
        _zones = zoneLiées;
    }

    @Override
    public ZoneInterface[] getZones() {
        return _zones;
    }

    @Override
    public boolean VérifierSiJourActuelEstBloqué() {
        return _calendrier.VérifierSiJourEstBloqué(_calendrier.JourActuel());
    }

    @Override
    public boolean peutOuvrir(List<String> badgeZone, String porteZone) {
        return badgeZone.stream().anyMatch(badge -> badge.equals(porteZone));
    }

    @Override
    public BadgeInterface getBadge() {
        return _badge;
    }
}
