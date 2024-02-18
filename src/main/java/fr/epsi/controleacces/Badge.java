package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.BadgeInterface;

import java.util.ArrayList;
import java.util.List;

public class Badge implements BadgeInterface {
    private boolean estBloqué = false;
    private String grade = "Visiteur";
    private List<String> _zones = new ArrayList<>();

    public Badge(String _grade) {
        switch (_grade) {
            case "Admin" -> {
                grade = "Admin";
                AffecterAZones(List.of("Accueil"));
            }
            case "Technicien" -> {
                grade = "Technicien";
                AffecterAZones(List.of("Accueil"));
            }
            case "Utilisateur" -> {
                grade = "Utilisateur";
                AffecterAZones(List.of("Accueil"));
            }
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
    public void AffecterAZones(List<String> zones) {
        for (String zone : zones) {
            if (!_zones.contains(zone)) {
                _zones.add(zone);
            }
        }
    }

    @Override
    public List<String> getZones() {
        return _zones;
    }
}
