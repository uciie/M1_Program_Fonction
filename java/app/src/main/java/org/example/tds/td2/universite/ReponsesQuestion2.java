package org.example.tds.td2.universite;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;

public class ReponsesQuestion2 {
        // 2.1
        public static void afficheSiSansStreamNiForEach(String entete, Predicate<Etudiant> pred, Annee annee) {
                String result = "**" + entete + "\n";
                for (Etudiant e : annee.etudiants()) {
                        if (pred.test(e))
                                result += e.toString() + "\n";
                }
                System.out.println(result);
        }

        // ici on utilise un forEach. Noter que TOUTE VARIABLE dans le Consumer
        // doit être déclarée final ou être en pratique "final".
        // Une variable String modifiée ne marcherait donc pas. On passe alors par un
        // StringBuilder.
        public static void afficheSiSansStream(String entete, Predicate<Etudiant> pred, Annee annee) {
                final StringBuilder result = new StringBuilder();
                result.append("**" + entete + "\n");
                annee.etudiants().forEach(e -> {
                        if (pred.test(e))
                                result.append(e.toString() + "\n");
                });
                System.out.println(result.toString());
        }

        // la version complète attendue avec API Function et API Stream
        public static void afficheSi(String entete, Predicate<Etudiant> pred, Annee annee) {
                String result = "**" + entete + "\n"
                                + annee.etudiants().stream()
                                                .filter(pred)
                                                .map(Etudiant::toString)
                                                .collect(Collectors.joining("\n"));
                // on pourrait rendre afficheSi functionnelle et faire la sortie ailleurs
                System.out.println(result);
        }

        // 2.2
        public static Predicate<Etudiant> aDEF = e -> e.annee().ues().stream()
                        .flatMap(ue -> ue.ects().keySet().stream())
                        .anyMatch(matiere -> !e.notes().keySet().contains(matiere));

        // 2.3
        public static Predicate<Etudiant> aNoteEliminatoire = e -> e.notes().values().stream()
                        .anyMatch(note -> note < 6.0);

        // 2.4
        public static Double moyenne(Etudiant etudiant) {
                // il y a plus propre
                // ici double parcours de liste pour les 2 calculs (pas efficace !)
                // on verra dans la suite du TD
                if (aDEF.test(etudiant)) // important !
                        return null; // on n'a pas vu Optional à ce niveau
                double sommePonderee = etudiant.annee().ues().stream()
                                .flatMap(ue -> ue.ects().entrySet().stream()
                                                .map(entry -> etudiant.notes().get(entry.getKey()) * entry.getValue()))
                                .reduce(0.0, (somme, notePonderee) -> somme + notePonderee);
                int sommeCoefficients = etudiant.annee().ues().stream()
                                .flatMap(ue -> ue.ects().values().stream())
                                .reduce(0, (somme, coefficient) -> somme + coefficient);
                return sommePonderee / sommeCoefficients;
        }

        // 2.5
        public static Predicate<Etudiant> naPasLaMoyennev1 = e -> moyenne(e) < 10.0;

        // 2.6 (noter les variantes)
        public static Predicate<Etudiant> naPasLaMoyennev2a = e -> aDEF.test(e) || moyenne(e) < 10;
        public static Predicate<Etudiant> naPasLaMoyennev2b = e -> aDEF.test(e) || naPasLaMoyennev1.test(e);

        // 2.7 (noter l'importance de aDEF avant le calcul de la moyenne mais pas de la
        // note éliminatoire)
        public static Predicate<Etudiant> session2v1a = naPasLaMoyennev1.or(aNoteEliminatoire).or(aDEF);
        public static Predicate<Etudiant> session2v1b = aNoteEliminatoire.or(aDEF).or(naPasLaMoyennev1);
        public static Predicate<Etudiant> session2v1c = aDEF.or(naPasLaMoyennev1).or(aNoteEliminatoire);

        // 2.8
        public static void afficheSiv2(String entete, Predicate<Etudiant> pred, Annee annee,
                        Function<Etudiant, String> representation) {
                String result = "**" + entete + "\n"
                                + annee.etudiants().stream()
                                                .filter(pred)
                                                .map(representation)
                                                .collect(Collectors.joining("\n"));
                // on pourrait rendre afficheSi functionnelle et faire la sortie ailleurs
                System.out.println(result);
        }

        // 2.9
        public static Double moyenneIndicative(Etudiant etudiant) {
                // noter le getOrDefault qui permet de fixer 0.0 si la note n'existe pas
                double sommePonderee = etudiant.annee().ues().stream()
                                .flatMap(ue -> ue.ects().entrySet().stream()
                                                .map(entry -> etudiant.notes().getOrDefault(entry.getKey(), 0.0)
                                                                * entry.getValue()))
                                .reduce(0.0, (somme, notePonderee) -> somme + notePonderee);
                int sommeCoefficients = etudiant.annee().ues().stream()
                                .flatMap(ue -> ue.ects().values().stream())
                                .reduce(0, (somme, coefficient) -> somme + coefficient);
                return sommePonderee / sommeCoefficients;
        }

        // 2.10
        public static Predicate<Etudiant> naPasLaMoyennevGeneralise(Function<Etudiant, Double> moyenne) {
                return e -> moyenne.apply(e) < 10;
        }

}
