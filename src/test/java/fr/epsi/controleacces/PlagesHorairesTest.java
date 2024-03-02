package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlagesHorairesTest {
    
    @Test
    void CasPlusieursPortesDontUneBloquéeDansPlageHoraire() {
        // ETANT DONNE un lecteur relié à deux portes dont une bloquée
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

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

    @Test
    void CasDansPlageHoraireBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake = new PorteFake(horloge);
        porteFake.DefinirPlageHoraire(8, 18);

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));
        badge.IntervertirBloquéDébloqué(); // badge bloqué

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // ET QUE l'heure actuelle est dans la plage horaire
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // QUAND ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasDansPlageHoraireSansBadge() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake = new PorteFake(horloge);
        porteFake.DefinirPlageHoraire(8, 18);

        var porteSpy = new PorteSpy(porteFake);
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

        // ET que ce lecteur est interrogé
        lecteurFake.DéfinirCommeVisiteur();
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }
}
