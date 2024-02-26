package fr.epsi.controleacces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControleAccesTest {

    @Nested
    @DisplayName("Assert true et cas nominal")
    public class Basic {
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

    @Nested
    @DisplayName("Badge bloqué ou débloqué")
    public class Badges {
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

    @Nested
    @DisplayName("Porte bloquée ou débloquée")
    public class Portes {
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

    @Nested
    @DisplayName("Lecteurs")
    public class Lecteurs {
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

    @Nested
    @DisplayName("Plages horaires")
    public class PlagesHoraires {
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

    @Nested
    @DisplayName("Jours de la semaine")
    public class JoursSemaine {
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

    @Nested
    @DisplayName("Grades : visiteur, utilisateur, technicien et admin (non demandé)")
    public class Grades {
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

    @Nested
    @DisplayName("Zone bloquée ou débloquée")
    public class Zones {
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
}
