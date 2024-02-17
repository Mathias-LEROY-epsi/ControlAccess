package fr.epsi.controleacces.utilities;

public interface BadgeInterface {
    void IntervertirBloquéDébloqué();

    boolean EstBloqué();

    String ObtenirGrade();

    void IntervertirGrade(String grade);

    void AffecterAZone(String zone);

    String getZone();
}
