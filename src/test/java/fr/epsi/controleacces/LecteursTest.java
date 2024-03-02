package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LecteursTest {
    @Test
    void CasPlusieursLecteurs() {
        // ETANT DONNE plusieurs lecteurs reliés à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy);
        var lecteurFake1 = new Lecteur(badge, calendrier, zone);
        var lecteurFake2 = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.VerifierLeGradeDuBadge(badge);
        lecteurFake2.simulerDétectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursLecteursPlusieursPortes() {
        // ETANT DONNE plusieurs lecteurs reliés chacun à leur porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(19);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);

        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy1);
        var zoneBis = new Zone("A", porteSpy2);
        var lecteurFake1 = new Lecteur(badge, calendrier, zone);
        var lecteurFake2 = new Lecteur(badge, calendrier, zoneBis);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.VerifierLeGradeDuBadge(badge);
        lecteurFake2.simulerDétectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS seule la deuxième porte s'ouvre
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }
}
