package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

import java.util.List;

public class Badge implements BadgeInterface {
    private boolean estBloqué = false;
    private String grade = "Utilisateur";

    private List<Zone> _zones;

    public Badge() {
    }

    @Override
    public void IntervertirBloquéDébloqué() {
        estBloqué = !estBloqué;
    }

    @Override
    public boolean EstBloqué() {
        return estBloqué;
    }

    @Override
    public String ObtenirGrade() {
        return grade;
    }

    @Override
    public void IntervertirGrade() {
        if (grade.equals("Utilisateur")) {
            grade = "Admin";
        } else {
            grade = "Utilisateur";
        }
    }

    @Override
    public void AffecterAZones(List<Zone> zones) {
        _zones = zones;
    }
}
