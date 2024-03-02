package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CasNominalTest {
    @Test
    void TestOk() {
        assertTrue(true);
    }

    @Test
    void CasNominal() {
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

        // ET QU'UN badge est passé devant le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);

        // QUAND ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }
}
