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
        var badge = new Badge("Utilisateur");

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

    @Test
    void CasBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
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
        horloge.DefinirHeureActuelle(18);
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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");

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
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

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
        horloge.DefinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge("Utilisateur");

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
        horloge.DefinirHeureActuelle(18);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        porteFake2.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var badge = new Badge("Utilisateur");

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

        var badge = new Badge("Utilisateur");
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
    void CasPlusieursLecteurs() {
        // ETANT DONNE plusieurs lecteurs reliés à une porte
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(18);
        var porteFake = new PorteFake(horloge);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteSpy = new PorteSpy(porteFake);
        var badge = new Badge("Utilisateur");
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
        horloge.DefinirHeureActuelle(19);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake1 = new PorteFake(horloge);
        var porteFake2 = new PorteFake(horloge);

        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);

        var badge = new Badge("Utilisateur");
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
        var badge = new Badge("Utilisateur");
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
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        var porteFake = new PorteFake(horloge);
        porteFake.DefinirPlageHoraire(8, 18);

        var porteSpy = new PorteSpy(porteFake);
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
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
        var badge = new Badge("Utilisateur");

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
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
        var badge = new Badge("Admin");

        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

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

        var badge = new Badge("Admin");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge admin est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasFermetureAutomatique() {
        // ETANT DONNE que toutes les portes sont fermées de 23h à minuit (maintenance)
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
        var badge = new Badge("Utilisateur");

        var zone = new Zone("A", porteSpy1, porteSpy2, porteSpy3);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
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
        var porteSpy1 = new PorteSpy(porteFake1);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var porteFake3 = new PorteFake(horloge);
        var porteSpy3 = new PorteSpy(porteFake3);

        var zone1 = new Zone("A", porteSpy1, porteSpy2);
        var zone2 = new Zone("B", porteSpy3);

        // ET qu'un badge présenté est lié à une zone
        var badge = new Badge("Utilisateur");
        badge.AffecterAZone("A");

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
    void CasBadgeTechnicienEnMaintenance() {
        // ETANT DONNE que toutes les portes sont fermées de 23h à minuit (maintenance)
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(23);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Technicien");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge technicien est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte s'ouvre
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeTechnicienHorsMaintenance() {
        // ETANT DONNE l'heure actuelle est dans la plage horaire
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Technicien");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge technicien est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte s'ouvre
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeSansGrade() {
        // ETANT DONNE l'heure actuelle est dans la plage horaire
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Toto");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge visiteur est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte s'ouvre
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteAccèsRéservéAuxTechnicienBadgeUtilisateur() {
        // ETANT DONNE un lecteur relié à une porte avec un accès réservé aux techniciens
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        porteFake.AccèsRéservéAuxTechniciens();
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Utilisateur");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge utilisateur est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteAccèsRéservéAuxTechnicienBadgeAdmin() {
        // ETANT DONNE un lecteur relié à une porte avec un accès réservé aux techniciens
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        porteFake.AccèsRéservéAuxTechniciens();
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Admin");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge admin est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteBloquéeHorsMaintenanceBadgeTechnicien() {
        // ETANT DONNE un lecteur lié à une porte bloquée hors maintenance
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que cette porte possède un accès réservé aux techniciens
        var porteFake = new PorteFake(horloge);
        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        porteFake.AccèsRéservéAuxTechniciens();
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Technicien");
        var zone = new Zone("A", porteSpy);
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge technicien est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte s'ouvre
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasZoneBloquéeBadgeUtilisateur() {
        // ETANT DONNE un lecteur relié à une zone qui regroupe des portes
        var horloge = new Horloge();
        horloge.DefinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que cette zone est bloquée
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var badge = new Badge("Utilisateur");
        var zone = new Zone("A", porteSpy, porteSpy2);
        zone.IntervertirBloquéDébloqué(); // zone bloquée
        var lecteurFake = new Lecteur(badge, calendrier, zone);

        // QUAND un badge utilisateur est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS aucune porte ne s'ouvre
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
    }
}
