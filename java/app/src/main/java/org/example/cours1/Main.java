package org.example.cours1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CalculateurPourboire calculateur = new CalculateurPourboire();
        
        List<String> noms = new ArrayList<>();
        noms.add("Alice");
        noms.add("Bob");
        noms.add("Charlie");
        noms.add("David");
        noms.add("Eve");
        noms.add("Frank");
        int pourcentage = calculateur.pourcentage(noms);

        System.out.println("Noms dans la liste: " + noms);
        System.out.println("Pourcentage de pourboire: " + pourcentage + "%");

        noms.remove("David");
        System.out.println("Noms après suppression: " + noms);
        System.out.println("Pourcentage de pourboire après suppression: " + calculateur.pourcentage(noms) + "%");
    }
}
