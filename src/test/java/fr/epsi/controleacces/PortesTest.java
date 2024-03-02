package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortesTest {

    @Test
    void CasPorteBloquée() {
        // ETANT DONNE une porte bloquée
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortes() {
        // ETANT DONNE un lecteur relié à deux portes
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy1, porteSpy2);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS les portes sont deverrouillées
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortesDontUneBloquée() {
        // ETANT DONNE un lecteur relié à deux portes dont une bloquée
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        porteFake2.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy1, porteSpy2);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seule la porte bloquée ne s'ouvre pas
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
    }
}
