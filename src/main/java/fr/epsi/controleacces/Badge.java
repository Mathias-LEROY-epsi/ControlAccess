package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

public class Badge implements BadgeInterface {
    private boolean estBloqué = false;
    private String grade = "Utilisateur";

    private String _zone = "A";

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
    public void AffecterAZone(String zone) {
        _zone = zone;
    }

    @Override
    public String getZone() {
        return _zone;
    }
}
