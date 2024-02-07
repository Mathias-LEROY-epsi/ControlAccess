import fr.enzosandre.controleacces.MoteurOuverture;
import fr.enzosandre.controleacces.utilities.LecteurFake;
import fr.enzosandre.controleacces.utilities.PorteSpy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControleAccesTest {
    @Test
    public void TestOk(){
        assertTrue(true);
    }

    @Test
    public void CasNominal() {
        // ETANT DONNE un lecteur relié à une porte
        var lecteurFake = new LecteurFake();
        var porteSpy = new PorteSpy();
        var moteurOuverture = new MoteurOuverture(porteSpy);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.SimulerDétectionBadge();

        // ET que ce lecteur est interrogé
        moteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est deverrouillée
        assertTrue(porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    public void CasSansInterrogation(){
        // ETANT DONNE un lecteur relié à une porte
        var lecteurFake = new LecteurFake();
        var porteSpy = new PorteSpy();
        var ignored = new MoteurOuverture(porteSpy);

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.SimulerDétectionBadge();

        // ALORS la porte n'est pas deverrouillée
        assertFalse(porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    public void CasSansDétection(){
        // ETANT DONNE un lecteur relié à une porte
        var lecteurFake = new LecteurFake();
        var porteSpy = new PorteSpy();
        var moteurOuverture = new MoteurOuverture(porteSpy);

        // QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        moteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas deverrouillée
        assertFalse(porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    public void CasPlusieursPortes(){
        // ETANT DONNE un lecteur relié à deux portes
        var lecteurFake = new LecteurFake();
        var porteSpy1 = new PorteSpy();
        var porteSpy2 = new PorteSpy();
        var moteurOuverture = new MoteurOuverture(porteSpy1, porteSpy2);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.SimulerDétectionBadge();

        // ET que ce lecteur est interrogé
        moteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS les portes sont deverrouillées
        assertTrue(porteSpy1.VérifierOuvertureDemandée());
        assertTrue(porteSpy2.VérifierOuvertureDemandée());
    }

    @Test
    public void CasPlusieursLecteurs() {
        // ETANT DONNE plusieurs lecteurs reliés à une porte
        var lecteurFake1 = new LecteurFake();
        var lecteurFake2 = new LecteurFake();
        var porteSpy = new PorteSpy();
        var moteurOuverture = new MoteurOuverture(porteSpy);

        // QUAND un badge est passé devant le deuxième lecteur
        lecteurFake2.SimulerDétectionBadge();

        // ET que ces lecteurs sont interrogés
        moteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);

        // ALORS la porte est deverrouillée
        assertTrue(porteSpy.VérifierOuvertureDemandée());
    }
}
