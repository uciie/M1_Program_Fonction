package org.example.cours1;

import java.util.List;

public class CalculateurPourboire {    
    public int pourcentage(List<String> names) {
        int pourcentage = 0;
        if (names == null || names.isEmpty()) {
            return pourcentage;
        }
        pourcentage = names.size() > 5 ? 20 : names.size() > 0 ? 10 : 0;
        return pourcentage;
    }
}
