package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;
import fr.epsi.controleacces.utilities.CalendrierInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

public class Lecteur implements LecteurInterface {
    private final PorteInterface[] _portes;
    private final BadgeInterface _badge;
    private final CalendrierInterface _calendrier;
    private boolean _estAdministrateur = false;
    private boolean _estTechnicien = false;
    private boolean _estUtilisateur = false;
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
    public boolean aDétectéBadge() {
        var val = _aDétectéBadge;
        _aDétectéBadge = false;
        return val;
    }

    @Override
    public boolean badgeBloqué() {
        return _badgeBloqué;
    }

    public Lecteur(BadgeInterface badge, CalendrierInterface calendrier, PorteInterface... portesLiées) {
        _badge = badge;
        _calendrier = calendrier;
        _portes = portesLiées;
    }

    @Override
    public PorteInterface[] getPortes() {
        return _portes;
    }

    @Override
    public boolean VérifierSiJourActuelEstBloqué() {
        return _calendrier.VérifierSiJourEstBloqué(_calendrier.JourActuel());
    }

    @Override
    public boolean peutOuvrir(String badgeZone, String porteZone) {
        return badgeZone.equals(porteZone);
    }

    @Override
    public BadgeInterface getBadge() {
        return _badge;
    }
}
