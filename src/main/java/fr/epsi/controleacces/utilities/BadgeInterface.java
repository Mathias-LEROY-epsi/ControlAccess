package fr.epsi.controleacces.utilities;

public interface BadgeInterface {
    void IntervertirBloquéDébloqué();

    boolean EstBloqué();

    String ObtenirGrade();

    void IntervertirGrade();

    void AffecterAZone(String zone);

    String getZone();
}
