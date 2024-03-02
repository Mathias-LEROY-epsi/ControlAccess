package fr.epsi.controleacces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests sur les grades (non souhaitée par le client)")
class GradesTest {
    @Test
    void CasBadgeAdmin() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(22);

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
        horloge.DéfinirHeureActuelle(23);

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
        horloge.DéfinirHeureActuelle(23);

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

        // QUAND un badge utilisateur est présenté
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS aucune porte ne s'ouvre
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy3.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeTechnicienEnMaintenance() {
        // ETANT DONNE que toutes les portes sont fermées de 23h à minuit (maintenance)
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(23);

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
        // ETANT DONNE que l'heure actuelle est dans la plage horaire
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

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
        // ETANT DONNE que l'heure actuelle est dans la plage horaire
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

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
        horloge.DéfinirHeureActuelle(12);

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
        horloge.DéfinirHeureActuelle(12);

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
    void CasPorteAccèsRéservéAuxVisiteurs() {
        // ETANT DONNE un lecteur relié à une porte avec un accès réservé aux visiteurs
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var zone = new Zone("Accueil", porteSpy);
        var zone2 = new Zone("A", porteSpy2);
        var lecteurFake = new Lecteur(null, calendrier, zone, zone2);

        // QUAND on interroge le lecteur
        lecteurFake.DéfinirCommeVisiteur();
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS seule la porte de l'accueil s'ouvre
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteAccèsRéservéAuxVisiteursBadgeAutreQueVisiteur() {
        // ETANT DONNE un lecteur relié à une porte avec un accès réservé aux visiteurs
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var porteFake2 = new PorteFake(horloge);
        var porteSpy2 = new PorteSpy(porteFake2);

        var zone = new Zone("Accueil", porteSpy);
        var zone2 = new Zone("A", porteSpy2);

        var badge = new Badge("Utilisateur");
        badge.AffecterAZones(List.of("A"));
        var lecteurFake = new Lecteur(badge, calendrier, zone, zone2);

        // QUAND on interroge le lecteur
        lecteurFake.VerifierLeGradeDuBadge(badge);
        lecteurFake.simulerDétectionBadge(badge);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS toutes les portes s'ouvrent
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteAccèsRéservéAuxAdminsBadgeutilisateur() {
        // ETANT DONNE un lecteur relié à une porte avec un accès réservé aux admins
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        porteFake.AccèsRéservéAuxAdmins();
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
    void CasPorteBloquéeHorsMaintenanceBadgeTechnicien() {
        // ETANT DONNE un lecteur lié à une porte bloquée hors maintenance
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(12);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que cette porte possède un accès réservé aux techniciens
        var porteFake = new PorteFake(horloge);
        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        porteFake.AccèsRéservéAuxTechniciens();
        var porteSpy = new PorteSpy(porteFake);

        var badge = new Badge("Technicien");
        badge.AffecterAZones(List.of("A"));

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
        horloge.DéfinirHeureActuelle(12);

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

    @Test
    void CasVisiteurEnMaintenance() {
        // ETANT DONNE que toutes les portes sont fermées de 23h à minuit (maintenance)
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(23);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET qu'un lecteur est relié à une porte
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var zone = new Zone("Accueil", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

        // QUAND un visiteur interroge le lecteur
        lecteurFake.DéfinirCommeVisiteur();
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasVisiteurHorsPlageHoraire() {
        // ETANT DONNE un lecteur relié à une porte
        var horloge = new Horloge();
        horloge.DéfinirHeureActuelle(19);

        var calendrier = new Calendrier();
        calendrier.InitialisationDesJoursBloqués();

        // ET que l'heure actuelle est hors de la plage horaire
        var porteFake = new PorteFake(horloge);
        var porteSpy = new PorteSpy(porteFake);

        var zone = new Zone("Accueil", porteSpy);
        var lecteurFake = new Lecteur(null, calendrier, zone);

        // QUAND un visiteur interroge le lecteur
        lecteurFake.DéfinirCommeVisiteur();
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre pas
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }
}
