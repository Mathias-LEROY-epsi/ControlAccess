package fr.epsi.controleacces.utilities;

import java.util.List;

public interface BadgeInterface {
    void IntervertirBloquéDébloqué();

    boolean EstBloqué();

    String ObtenirGrade();

    void AffecterAZones(List<String> zones);

    List<String> getZones();
}
