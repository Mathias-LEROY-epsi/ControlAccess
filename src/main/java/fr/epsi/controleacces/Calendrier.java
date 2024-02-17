package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.CalendrierInterface;
import javafx.util.Pair;

import java.util.List;

public class Calendrier implements CalendrierInterface {

    private List<Pair<String, Boolean>> joursBloques;

    private String jourActuel = "lundi";

    public Calendrier() {
    }

    @Override
    public void DéfinirJourActuel(String jour) {
        jourActuel = jour;
    }

    @Override
    public String JourActuel() {
        return jourActuel;
    }


    @Override
    public void InitialisationDesJoursBloqués() {
        // Initialisation des jours de la semaine avec la valeur par défaut false (sauf week-end)
        joursBloques = List.of(
                new Pair<>("lundi", false),
                new Pair<>("mardi", false),
                new Pair<>("mercredi", false),
                new Pair<>("jeudi", false),
                new Pair<>("vendredi", false),
                new Pair<>("samedi", true),
                new Pair<>("dimanche", true)
        );
    }

    @Override
    public void BloquerJour(List<String> jours) {
        joursBloques = joursBloques.stream()
                .map(j -> jours.contains(j.getKey()) ? new Pair<>(j.getKey(), true) : j)
                .toList();
    }

    @Override
    public void DébloquerJour(List<String> jours) {
        joursBloques = joursBloques.stream()
                .map(j -> jours.contains(j.getKey()) ? new Pair<>(j.getKey(), false) : j)
                .toList();
    }

    @Override
    public boolean VérifierSiJourEstBloqué(String jour) {
        for (Pair<String, Boolean> j : joursBloques) {
            if (j.getKey().equals(jour)) {
                return j.getValue();
            }
        }
        // sécurité dans le cas où l'on renseigne n'importe quoi
        return true;
    }
}
