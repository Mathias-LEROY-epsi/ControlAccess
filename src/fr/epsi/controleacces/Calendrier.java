package fr.epsi.controleacces;

import fr.epsi.controleacces.utilities.CalendrierInterface;

import java.util.HashMap;
import java.util.Map;

public class Calendrier implements CalendrierInterface {

    private Map<String, Boolean> joursBloques = new HashMap<>();

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
        joursBloques.put("lundi", false);
        joursBloques.put("mardi", false);
        joursBloques.put("mercredi", false);
        joursBloques.put("jeudi", false);
        joursBloques.put("vendredi", false);
        joursBloques.put("samedi", true);
        joursBloques.put("dimanche", true);
    }

    @Override
    public void BloquerJour(String[] jour) {
        for (String j : jour) {
            if (!joursBloques.containsKey(j)) {
                continue;
            }
            joursBloques.put(j, true);
        }
    }

    @Override
    public void DébloquerJour(String[] jour) {
        for (String j : jour) {
            if (!joursBloques.containsKey(j)) {
                continue;
            }
            joursBloques.put(j, false);
        }
    }

    @Override
    public boolean VérifierSiJourEstBloqué(String jour) {
        return joursBloques.get(jour);
    }
}
