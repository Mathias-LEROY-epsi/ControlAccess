package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.CalendrierInterface;
import fr.epsi.controleacces.utilities.LecteurInterface;
import fr.epsi.controleacces.utilities.PorteInterface;

public class Lecteur implements LecteurInterface {
    private final PorteInterface[] _portes;
    private final CalendrierInterface _calendrier;
    private boolean _estAdministrateur = false;
    private boolean _aDétectéBadge = false;
    private boolean _badgeBloqué = false;

    public void simulerDétectionBadge(Badge badge) {
        _aDétectéBadge = true;
        _badgeBloqué = badge.EstBloqué();
    }

    public void VérifierSiBagdeEstAdministrateur(Badge badge) {
        if (badge.ObtenirGrade().equals("Admin")) {
            _estAdministrateur = true;
        }
    }

    @Override
    public boolean VérifierSiBagdeEstAdministrateur() {
        return _estAdministrateur;
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

    public Lecteur(CalendrierInterface calendrier, PorteInterface... portesLiées) {
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
}
