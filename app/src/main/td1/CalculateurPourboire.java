import java.util.ArrayList;
import java.util.List;
/*
public class CalculateurPourboire {
    private List<String> noms = new ArrayList<>();
    private int pourcentage = 0;

    public void ajouterPersonne(String nom) {
        noms.add(nom);
    }
    public List<String> noms() { return noms; }
    public int pourcentage() { return pourcentage; }
}
*/

public class CalculateurPourboire {
    public int pourcentage(List<String> noms) { 
        int pourcentage = 0;
        if ((noms == null || noms.isEmpty())) {
            return pourcentage;
        }
        pourcentage = noms.size() > 5 ? 20 : noms.size() > 0 ? 10 : 0;
        return pourcentage;
    }
}
