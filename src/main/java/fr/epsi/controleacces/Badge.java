package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

public class Badge implements BadgeInterface {
    private boolean estBloqué = false;
    private String grade = "Visiteur";

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
    public void IntervertirGrade(String _grade) {
        if (_grade.equals("Admin")) {
            grade = "Admin";
        } else if (_grade.equals("Technicien")) {
            grade = "Technicien";
        } else if (_grade.equals("Utilisateur")) {
            grade = "Utilisateur";
        } else {
            grade = "Visiteur";
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
