package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControleAccesTest {
    @Test
    void TestOk() {
        assertTrue(true);
    }

    @Test
    void CasNominal() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        badge.IntervertirBloquéDébloqué(); // badge bloqué
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge bloqué est passé devant le lecteur
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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        badge.IntervertirBloquéDébloqué(); // badge bloqué
        badge.IntervertirBloquéDébloqué(); // badge débloqué

        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge bloqué puis débloqué est passé devant le lecteur
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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.simulerDétectionBadge(badge);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursInterrogations() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est présenté
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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var lecteurFake = new Lecteur(null, calendrier, porteSpy);

        // QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteBloquée() {
        // ETANT DONNE une porte bloquée
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est présenté
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
        horloge.DefinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy1, porteSpy2);

        // QUAND un badge est passé devant le lecteur
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
        horloge.DefinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        porteFake2.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy1, porteSpy2);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seule la porte bloquée ne s'ouvre pas
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortesDontUneBloquéeDansPlageHoraire() {
        // ETANT DONNE un lecteur relié à deux portes dont une bloquée
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        porteFake2.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);

        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy1, porteSpy2);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seule la porte bloquée ne s'ouvre pas
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursLecteurs() {
        // ETANT DONNE plusieurs lecteurs reliés à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();

        var lecteurFake1 = new Lecteur(badge, calendrier, porteSpy);
        var lecteurFake2 = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est passé devant le deuxième lecteur
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
        horloge.DefinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);

        var badge = new Badge();
        var lecteurFake1 = new Lecteur(badge, calendrier, porteSpy1);
        var lecteurFake2 = new Lecteur(badge, calendrier, porteSpy2);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.simulerDétectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS seule la deuxième porte s'ouvre
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasDansPlageHoraireBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake = new PorteFake(horloge);
        porteFake.DefinirPlageHoraire(8, 18);

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        badge.IntervertirBloquéDébloqué(); // badge bloqué
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // ET QUE l'heure actuelle est dans la plage horaire
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
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake = new PorteFake(horloge);
        porteFake.DefinirPlageHoraire(8, 18);

        var porteSpy = new PorteSpy(porteFake);
        var lecteurFake = new Lecteur(null, calendrier, porteSpy);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasDansLaSemaine() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(22);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que le lecteur est interrogé un jour de la semaine
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeAdmin() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(22);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que le lecteur est interrogé
        var porteFake = new PorteFake(horloge);
        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge();
        badge.IntervertirGrade(); // badge admin
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge admin est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeAdminMaintenance() {
        // ETANT DONNE que toutes les portes sont fermées de 22h à minuit (maintenance)
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(23);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge();
        badge.IntervertirGrade(); // badge admin
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy);

        // QUAND un badge admin est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasFermetureAutomatique() {
        // ETANT DONNE que toutes les portes sont fermées de 22h à minuit (maintenance)
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(23);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake1 = new PorteFake(horloge);
        var porteSpy1 = new PorteSpy(porteFake1);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var porteFake3 = new PorteFake(horloge);
        var porteSpy3 = new PorteSpy(porteFake3);
        var badge = new Badge();
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy1, porteSpy2, porteSpy3);

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS aucune porte ne s'ouvre
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy3.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortesLieesAPlusieursZones() {
        // ETANT DONNE des portes de plusieurs zones
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        porteFake1.AssocierZone("A");
        var porteSpy1 = new PorteSpy(porteFake1);

        var porteFake2 = new PorteFake(horloge);
        porteFake2.AssocierZone("A");
        var porteSpy2 = new PorteSpy(porteFake2);

        var porteFake3 = new PorteFake(horloge);
        porteFake3.AssocierZone("B");
        var porteSpy3 = new PorteSpy(porteFake3);

        // ET qu'un badge présenté est lié à une zone
        var badge = new Badge();
        badge.AffecterAZone("A");

        // Quand un lecteur est relié à des portes de chaque zone
        var lecteurFake = new Lecteur(badge, calendrier, porteSpy1, porteSpy2, porteSpy3);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seules les portes de cette zone s'ouvrent
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy3.VérifierOuvertureDemandée());
    }
}
