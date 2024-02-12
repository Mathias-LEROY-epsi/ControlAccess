package fr.epsi.controleacces.utilities;

public interface PorteInterface {
    void Ouvrir();
    void IntervertirBloquéDébloqué();
    boolean EstBloquée();
    void DefinirPlageHoraire(Integer heureDébut, Integer heureFin);
    boolean EstDansPlageHoraire();
}
