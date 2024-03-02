package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoursSemaineTest {
    @Test
    void CasDansLaSemaine() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(22);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que le lecteur est interrogé un jour de la semaine
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }
}
