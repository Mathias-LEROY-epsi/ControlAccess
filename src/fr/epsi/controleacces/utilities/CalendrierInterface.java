package fr.epsi.controleacces.utilities;

public interface CalendrierInterface {
    void DéfinirJourActuel(String jour);

    String JourActuel();

    void InitialisationDesJoursBloqués();

    void BloquerJour(String[] jour);

    void DébloquerJour(String[] jour);

    boolean VérifierSiJourEstBloqué(String jour);
}
