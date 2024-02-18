package fr.epsi.controleacces.utilities;

public interface PorteInterface {
    void Ouvrir();

    void DefinirFermetureMaintenance(Integer _heureDébut, Integer _heureFin);

    void IntervertirBloquéDébloqué();

    boolean EstBloquée();

    void DefinirPlageHoraire(Integer heureDébut, Integer heureFin);

    boolean EstEnMaintenance();

    boolean EstDansPlageHoraire();

    boolean EstUnAccèsRéservéAuxTechniciens();

    boolean EstUnAccèsRéservéAuxAdmins();
}
