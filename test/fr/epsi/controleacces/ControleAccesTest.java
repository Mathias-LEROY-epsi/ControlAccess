package fr.epsi.controleacces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControleAccesTest {
    @Test
    void TestOk(){
        assertTrue(true);
    }

    @Test
    void CasNominal() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge();

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
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);

        var badge = new Badge();
        badge.IntervertirBloquéDébloqué(); // badge bloqué

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
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);

        var badge = new Badge();
        badge.IntervertirBloquéDébloqué(); // badge bloqué
        badge.IntervertirBloquéDébloqué(); // badge débloqué

        // QUAND un badge bloqué puis débloqué est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansInterrogation(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);

        var badge = new Badge();

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.simulerDétectionBadge(badge);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursInterrogations(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);

        var badge = new Badge();

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé deux fois
        MoteurOuverture.InterrogerLecteurs(lecteurFake);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre qu'une fois
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansDétection(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy);

        // QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteBloquée() {
        // ETANT DONNE une porte bloquée
        var porteFake = new PorteFake();
        porteFake.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy = new PorteSpy(porteFake);
        var lecteurFake = new LecteurFake(porteSpy);

        var badge = new Badge();

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortes(){
        // ETANT DONNE un lecteur relié à deux portes
        var porteSpy1 = new PorteSpy(new PorteFake());
        var porteSpy2 = new PorteSpy(new PorteFake());
        var lecteurFake = new LecteurFake(porteSpy1, porteSpy2);

        var badge = new Badge();

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS les portes sont deverrouillées
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortesDontUneBloquée(){
        // ETANT DONNE un lecteur relié à deux portes dont une bloquée
        var porteFake1 = new PorteFake();
        var porteFake2 = new PorteFake();
        porteFake2.IntervertirBloquéDébloqué(); // porte bloquée
        var porteSpy1 = new PorteSpy(porteFake1);
        var porteSpy2 = new PorteSpy(porteFake2);
        var lecteurFake = new LecteurFake(porteSpy1, porteSpy2);

        var badge = new Badge();

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
        var porteSpy = new PorteSpy(new PorteFake());
        var lecteurFake1 = new LecteurFake(porteSpy);
        var lecteurFake2 = new LecteurFake(porteSpy);

        var badge = new Badge();

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
        var porteSpy1 = new PorteSpy(new PorteFake());
        var porteSpy2 = new PorteSpy(new PorteFake());
        var lecteurFake1 = new LecteurFake(porteSpy1);
        var lecteurFake2 = new LecteurFake(porteSpy2);

        var badge = new Badge();

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.simulerDétectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS seule la deuxième porte s'ouvre
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }
}
