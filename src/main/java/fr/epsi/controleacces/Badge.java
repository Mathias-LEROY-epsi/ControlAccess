package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

public class Badge implements BadgeInterface {
    private boolean estBloqué = false;
    private String grade = "Visiteur";

    private String _zone = "A";

    public Badge(String _grade) {
        switch (_grade) {
            case "Admin" -> grade = "Admin";
            case "Technicien" -> grade = "Technicien";
            case "Utilisateur" -> grade = "Utilisateur";
            default -> grade = "Visiteur";
        }
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
    public void AffecterAZone(String zone) {
        _zone = zone;
    }

    @Override
    public String getZone() {
        return _zone;
    }
}
