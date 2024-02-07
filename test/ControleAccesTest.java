import fr.enzosandre.controleacces.MoteurOuverture;
import fr.enzosandre.controleacces.utilities.LecteurFake;
import fr.enzosandre.controleacces.utilities.PorteSpy;
import jdk.jshell.spi.ExecutionControl;
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
        var porteSpy = new PorteSpy(lecteurFake);
        var moteurOuverture = new MoteurOuverture(porteSpy);

        // QUAND un badge est passé devant le lecteur
        lecteurFake.SimulerDétectionBadge();

        // ET que ce lecteur est interrogé
        moteurOuverture.InterrogerLecteur(lecteurFake);

        // ALORS la porte est deverrouillée
        assertTrue(porteSpy.VérifierOuvertureDemandée());
    }

    @Test
    public void CasSansInterrogation(){
        // ETANT DONNE un lecteur relié à une porte
        var lecteurFake = new LecteurFake();
        var porteSpy = new PorteSpy(lecteurFake);
        var ignored = new MoteurOuverture(porteSpy);

        // QUAND un badge est passé devant le lecteur sans que le lecteur ne soit interrogé
        lecteurFake.SimulerDétectionBadge();

        // ALORS la porte n'est pas deverrouillée
        assertFalse(porteSpy.VérifierOuvertureDemandée());
    }
}
