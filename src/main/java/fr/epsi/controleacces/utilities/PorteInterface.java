package fr.epsi.controleacces.utilities;

public interface PorteInterface {
    void Ouvrir();

    void DefinirFermetureMaintenance(Integer _heureDébut, Integer _heureFin);

    String getZone();

    void IntervertirBloquéDébloqué();

    boolean VerifierSiPorteBloquée();

    void DefinirPlageHoraire(Integer heureDébut, Integer heureFin);

    boolean EstEnMaintenance();

    boolean EstDansPlageHoraire();

    boolean VérifierSiAccèsRéservéAuxTechniciens();
}
