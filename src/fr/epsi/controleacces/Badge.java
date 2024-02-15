package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

public class Badge implements BadgeInterface {
    public boolean estBloqué = false;

    public String grade = "Utilisateur";

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
}
