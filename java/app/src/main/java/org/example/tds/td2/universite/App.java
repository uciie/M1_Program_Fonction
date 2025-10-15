package org.example.tds.td2.universite;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;

import static org.example.tds.td2.universite.ReponsesQuestion2.*;
import static org.example.tds.td2.universite.ReponsesQuestion3.*;

public class App {
    public static void main(String[] args) {
        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
        e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        System.out.println(e1);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
        e2.noter(m1, 14.0);
        e2.noter(m3, 14.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
        e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);

        // 2.1
        afficheSiSansStreamNiForEach("TOUS LES ETUDIANTS (afficheSi sans Stream ni forEach)", e -> true, a1);
        afficheSiSansStream("TOUS LES ETUDIANTS (afficheSi sans Stream)", e -> true, a1);
        afficheSi("TOUS LES ETUDIANTS (afficheSi complet)", e -> true, a1);

        // 2.2
        afficheSi("ETUDIANTS DEFAILLLANTS", aDEF, a1);

        // 2.3
        afficheSi("ETUDIANTS AVEC NOTE ELIMINATOIRE", aNoteEliminatoire, a1);

        // 2.4
        for (Etudiant e : List.of(e1, e2, e3)) {
            System.out.println(e);
            System.out.println(moyenne(e));
        }

        // 2.5
        try {
            afficheSi("ETUDIANTS N'AYANT PAS LA MOYENNE (v1)", naPasLaMoyennev1, a1);
        } catch (Exception e) {
            // juste pour l'exercice !
            System.out.println("un problème : " + e.getMessage());
        }

        // 2.6
        afficheSi("ETUDIANTS N'AYANT PAS LA MOYENNE (v2.a)", naPasLaMoyennev2a, a1);
        afficheSi("ETUDIANTS N'AYANT PAS LA MOYENNE (v2.b)", naPasLaMoyennev2b, a1);

        // 2.7
        try {
            afficheSi("ETUDIANTS EN SESSION 2", session2v1a, a1);
        } catch (Exception e) {
            // juste pour l'exercice!
            System.out.println("un problème : " + e.getMessage());
        }
        afficheSi("ETUDIANTS EN SESSION 2", session2v1b, a1);
        afficheSi("ETUDIANTS EN SESSION 2", session2v1c, a1);

        // 2.8
        Function<Etudiant, String> representationSimple = e -> {
            Double moyenne = moyenne(e);
            return String.format("%s %s : %s", e.prenom(), e.nom(), (moyenne != null) ? moyenne : "défaillant");
        };
        afficheSiv2("TOUS LES ETUDIANTS", e -> true, a1, Etudiant::toString);
        afficheSiv2("TOUS LES ETUDIANTS", e -> true, a1, representationSimple);

        // 2.9
        Function<Etudiant, String> representationSimpleIndicative = e -> {
            Double moyenne = moyenneIndicative(e); // ne peut plus être null
            return String.format("%s %s : %s", e.prenom(), e.nom(), moyenne);
        };
        afficheSiv2("TOUS LES ETUDIANTS (moyenne indicative)", e -> true, a1, representationSimpleIndicative);

        // 2.10
        afficheSiv2("TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyennevGeneralise(ReponsesQuestion2::moyenneIndicative), a1, representationSimple);
        afficheSiv2("TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyennevGeneralise(ReponsesQuestion2::moyenneIndicative), a1, representationSimpleIndicative);
        e2.noter(m1, 20.0);
        e2.noter(m3, 20.0);
        afficheSiv2("TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE", naPasLaMoyennevGeneralise(ReponsesQuestion2::moyenneIndicative), a1, representationSimpleIndicative);
    }
}
