package org.example.tds.td2.universite;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.BinaryOperator;

import static org.example.tds.td2.universite.ReponsesQuestion2.aDEF;

public class ReponsesQuestion3 {

        // matières d'une année
        public static final Function<Annee, Stream<Matiere>> matieresA = a -> a.ues().stream()
                        .flatMap(ue -> ue.ects().keySet().stream());

        // matières d'un étudiant
        public static final Function<Etudiant, Stream<Matiere>> matieresE = e -> matieresA.apply(e.annee());

        // matières coefficientées d'un étudiant (version Entry)
        public static final Function<Etudiant, Stream<Entry<Matiere, Integer>>> matieresCoefE_ = e -> e.annee().ues()
                        .stream()
                        .flatMap(ue -> ue.ects().entrySet().stream());

        // transformation d'une Entry en une Paire
        public static final Function<Entry<Matiere, Integer>, Paire<Matiere, Integer>> entry2paire = en -> new Paire<>(
                        en.getKey(), en.getValue());

        // matières coefficientées d'un étudiant (version Paire)
        public static final Function<Etudiant, Stream<Paire<Matiere, Integer>>> matieresCoefE = e -> matieresCoefE_
                        .apply(e)
                        .map(entry2paire);

        // accumulateur pour calcul de la moyenne
        // ((asomme, acoef), (note, coef)) -> (asomme+note*coef, acoef+coef)
        public static final BinaryOperator<Paire<Double, Integer>> accumulateurMoyenne = (acc,
                        nv) -> new Paire<>(acc.fst() + nv.fst() * nv.snd(), acc.snd() + nv.snd());

        // zero (valeur initiale pour l'accumulateur)
        public static final Paire<Double, Integer> zero = new Paire<>(0.0, 0);

        // obtention de la liste de (note, coef) pour les matières d'un étudiant
        // 1. obtenir les (matière, coef)s
        // 2. mapper pour obtenir les (note, coef)s, 0.0 pour la note si l'étudiant est
        // DEF dans cette matière
        public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPonderees = e -> matieresCoefE
                        .apply(e)
                        .map(p -> new Paire<>(e.notes().get(p.fst()), p.snd())).collect(Collectors.toList());

        // obtention de la liste de (note, coef) pour les matières d'un étudiant
        // 1. obtenir les (matière, coef)s
        // 2. mapper pour obtenir les (note, coef)s, 0.0 pour la note si l'étudiant est
        // DEF dans cette matière
        public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPondereesIndicatives = e -> matieresCoefE
                        .apply(e)
                        .map(p -> new Paire<>(
                                        e.notes().getOrDefault(p.fst(), 0.0),
                                        // un version plus lourde:
                                        // (e.notes().containsKey(p.fst())) ? (e.notes().get(p.fst())) : 0.0,
                                        p.snd()))
                        .collect(Collectors.toList());

        // replie avec l'accumulateur spécifique
        public static final Function<List<Paire<Double, Integer>>, Paire<Double, Integer>> reduit = ps -> ps.stream()
                        .reduce(zero, accumulateurMoyenne);

        // calcule la moyenne à partir d'un couple (somme pondérée, somme coefs)
        public static final Function<Paire<Double, Integer>, Double> divise = p -> p.fst() / p.snd();

        // calcul de moyenne fonctionnel
        // composer notesPonderees, reduit et divise
        // exception en cas de matière DEF
        public static final Function<Etudiant, Double> computeMoyenne = notesPonderees.andThen(reduit).andThen(divise);

        // calcul de moyenne fonctionnel
        // composer notesPondereesIndicatives, reduit et divise
        // pas d'exception en cas de matière DEF
        public static final Function<Etudiant, Double> computeMoyenneIndicative = notesPondereesIndicatives
                        .andThen(reduit).andThen(divise);

        // calcul de moyenne (sert juste de précondition à computeMoyenne)
        public static final Function<Etudiant, Double> moyenne = e -> (e == null || aDEF.test(e)) ? null
                        : computeMoyenne.apply(e);
}
