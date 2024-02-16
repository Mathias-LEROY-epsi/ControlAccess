package fr.epsi.controleacces.utilities;

import java.util.List;

public interface CalendrierInterface {
    void DéfinirJourActuel(String jour);

    String JourActuel();

    void InitialisationDesJoursBloqués();

    void BloquerJour(List<String> jour);

    void DébloquerJour(List<String> jour);

    boolean VérifierSiJourEstBloqué(String jour);
}
