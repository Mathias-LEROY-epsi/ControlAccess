package fr.epsi.controleacces.utilities;

import fr.epsi.controleacces.Zone;

import java.util.List;

public interface BadgeInterface {
    void IntervertirBloquéDébloqué();

    boolean EstBloqué();

    String ObtenirGrade();

    void IntervertirGrade();

    void AffecterAZones(List<Zone> zones);
}
