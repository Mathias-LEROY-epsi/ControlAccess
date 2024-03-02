package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadgesTest {

    @Test
    void CasBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.IntervertirBloquéDébloqué(); // badge bloqué

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge bloqué est passé devant le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeBloquéPuisDébloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));
        badge.IntervertirBloquéDébloqué(); // badge bloqué
        badge.IntervertirBloquéDébloqué(); // badge débloqué

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge bloqué puis débloqué est passé devant le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansInterrogation() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursInterrogations() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé deux fois
        MoteurOuverture.InterrogerLecteurs(lecteurFake);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre qu'une fois
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansDétection() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

        // QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        lecteurFake.DéfinirCommeVisiteur();
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }
}
