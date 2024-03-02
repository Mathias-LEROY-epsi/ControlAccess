package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZonesTest {
    @Test
    void CasPlusieursPortesLieesAPlusieursZonesBadgeUtilisateur() {
        // ETANT DONNE des portes de plusieurs zones
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteSpy1 = new PorteSpy(porteFake1);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var porteFake3 = new PorteFake(horloge);
        var porteSpy3 = new PorteSpy(porteFake3);

        var zone1 = new Zone("A", porteSpy1, porteSpy2);
        var zone2 = new Zone("B", porteSpy3);

        // ET qu'un badge utilisateur présenté est lié à une zone
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        // Quand un lecteur est relié à des portes de chaque zone
        var lecteurFake = new Lecteur(badge, calendrier, zone1, zone2);
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seules les portes de cette zone s'ouvrent
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy3.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortesLieesAPlusieursZonesBadgeTechnicien() {
        // ETANT DONNE des portes de plusieurs zones
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        porteFake1.AccèsRéservéAuxTechniciens();
        var porteSpy1 = new PorteSpy(porteFake1);

        var porteFake2 = new PorteFake(horloge);
        porteFake2.AccèsRéservéAuxTechniciens();
        var porteSpy2 = new PorteSpy(porteFake2);

        var porteFake3 = new PorteFake(horloge);
        porteFake3.AccèsRéservéAuxTechniciens();
        var porteSpy3 = new PorteSpy(porteFake3);

        var zone1 = new Zone("A", porteSpy1, porteSpy2);
        var zone2 = new Zone("B", porteSpy3);

        // ET qu'un badge technicien présenté est lié à une zone
        var badge = new Badge("Technicien");
        badge.AffecterAZones(List.of("A"));

        // Quand un lecteur est relié à des portes de chaque zone
        var lecteurFake = new Lecteur(badge, calendrier, zone1, zone2);
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seules les portes de cette zone s'ouvrent
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy3.VérifierOuvertureDemandée());
    }
}
