import fr.epsi.controleacces.MoteurOuverture;
import fr.epsi.controleacces.utilities.Badge;
import fr.epsi.controleacces.utilities.LecteurFake;
import fr.epsi.controleacces.utilities.PorteFake;
import fr.epsi.controleacces.utilities.PorteSpy;
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
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge(false);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeBloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge(true);

        // QUAND un badge bloqué est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasBadgeBloquéPuisDébloqué() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);
        var badgeBloqué = new Badge(true);
        var badgeDébloqué = new Badge(false);

        // QUAND un badge bloqué puis débloqué est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badgeBloqué);
        lecteurFake.simulerDétectionBadge(badgeDébloqué);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansInterrogation(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge(false);

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursInterrogations(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge(false);

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ET que ce lecteur est interrogé deux fois
        MoteurOuverture.InterrogerLecteurs(lecteurFake);
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte ne s'ouvre qu'une fois
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasSansDétection(){
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy);

        // QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        MoteurOuverture.InterrogerLecteurs(lecteurFake);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPorteBloquée() {
        // ETANT DONNE une porte bloquée
        var porteSpy = new PorteSpy(new PorteFake(true));
        var lecteurFake = new LecteurFake(porteSpy);
        var badge = new Badge(false);

        // QUAND un badge est présenté
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertEquals(0, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursPortes(){
        // ETANT DONNE un lecteur relié à deux portes
        var porteSpy1 = new PorteSpy(new PorteFake(false));
        var porteSpy2 = new PorteSpy(new PorteFake(false));
        var lecteurFake = new LecteurFake(porteSpy1, porteSpy2);
        var badge = new Badge(false);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.simulerDétectionBadge(badge);
        lecteurFake.simulerBloquagePorte(porteSpy1);
        lecteurFake.simulerBloquagePorte(porteSpy2);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS les portes sont deverrouillées
        assertEquals(1, porteSpy1.VérifierOuvertureDemandée());
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursLecteurs() {
        // ETANT DONNE plusieurs lecteurs reliés à une porte
        var porteSpy = new PorteSpy(new PorteFake(false));
        var lecteurFake1 = new LecteurFake(porteSpy);
        var lecteurFake2 = new LecteurFake(porteSpy);
        var badge = new Badge(false);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.simulerDétectionBadge(badge);
        lecteurFake1.simulerBloquagePorte(porteSpy);
        lecteurFake2.simulerBloquagePorte(porteSpy);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS la porte est deverrouillée
        assertEquals(1, porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    void CasPlusieursLecteursPlusieursPortes() {
        // ETANT DONNE plusieurs lecteurs reliés chacun à leur porte
        var porteSpy1 = new PorteSpy(new PorteFake(false));
        var porteSpy2 = new PorteSpy(new PorteFake(false));
        var lecteurFake1 = new LecteurFake(porteSpy1);
        var lecteurFake2 = new LecteurFake(porteSpy2);
        var badge = new Badge(false);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.simulerDétectionBadge(badge);
        lecteurFake1.simulerBloquagePorte(porteSpy1);
        lecteurFake2.simulerBloquagePorte(porteSpy2);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS seule la deuxième porte s'ouvre
        assertEquals(1, porteSpy2.VérifierOuvertureDemandée());
        assertEquals(0, porteSpy1.VérifierOuvertureDemandée());
    }
}
