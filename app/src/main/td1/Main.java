import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CalculateurPourboire calculateur = new CalculateurPourboire();

        ArrayList<String> personnes = new ArrayList<>(List.of("Jean", "Marie", "Paul", "Sophie", "Luc", "Emma"));

        System.out.println("Tips initiale: " + calculateur.pourcentage(null));

/*
        for (String personne : personnes) {
            calculateur.ajouterPersonne(personne);
        }
        */
        System.out.println("Liste des personnes: " + personnes);
        System.out.println("Tips finale: " + calculateur.pourcentage(personnes));
        System.out.println();

        personnes.remove("Emma");
        System.out.println("Liste des personnes après suppression: " + personnes);
        System.out.println("Tips après suppression: " + calculateur.pourcentage(personnes));
    }
}