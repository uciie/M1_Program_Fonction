package org.example.cours2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Cours2 {
    /*
    static int score(String s) {
        return s.replaceAll("a", "").length();
    }
    */

    /* ====== SOLUTION 1 ====== 
    static Comparator<String> scoreComparator = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(score(s2), score(s1));
        }
    };

    static List<String> rankedWords(List<String> words) {
        words.sort(scoreComparator);
        return words;
    }
    
    public static void main2() {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(words));
    }
    */

    /* ====== SOLUTION 2 ====== 
    
    static Comparator<String> scoreComparator = 
        (s1, s2) -> Integer.compare(score(s2), score(s1));

    static List<String> rankedWords(Comparator<String> comparator, List<String> words) {
        List<String> sortedWords = new ArrayList<>(words);
        sortedWords.sort(comparator);
        return sortedWords;
    }

    public static void main2() {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(scoreComparator, words));
    }
    */

    /* ====== SOLUTION 3 ====== 
    static int scoreWithBonus(String s) {
        return score(s) + (s.contains("c") ? 5 : 0);
    }

    static Comparator<String> scoreComparator = 
        (s1, s2) -> Integer.compare(score(s2), score(s1));

    static Comparator<String> scoreComparatorWithBonus = 
        (s1, s2) -> Integer.compare(scoreWithBonus(s2), scoreWithBonus(s1));

    static List<String> rankedWords(Comparator<String> comparator, List<String> words) {
        List<String> sortedWords = new ArrayList<>(words);
        sortedWords.sort(comparator);
        return sortedWords;
    }

    public static void main3() {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(scoreComparator, words));
        System.out.println(rankedWords(scoreComparatorWithBonus, words));
    }
    */
    
    /* ====== SOLUTION 4 ====== 
    static Function<String, Integer> score = 
        s -> s.replaceAll("a", "").length();

    public static void main4() {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(Comparator.comparing(score).reversed(), words));
        System.out.println(rankedWords(Comparator.comparing(score).thenComparing(s -> s.contains("c") ? 5 : 0).reversed(), words));
    }
    */

    /* ====== SOLUTION 5 ====== 
    static List<String> rankedWords(Comparator<String> comparator, List<String> words) {
        List<String> sortedWords = new ArrayList<>(words);
        sortedWords.sort(comparator);
        return sortedWords;
    }
    
    static int scoreWithBonus(String s) {
        return score.apply(s) + (s.contains("c") ? 5 : 0);
    }

    static Function<String, Integer> score = 
        s -> s.replaceAll("a", "").length();

    static Function<String, Integer> scoreWithBonusFunc = 
        s -> score.apply(s) + (s.contains("c") ? 5 : 0);
    
    static Comparator<String> genComparator(Function<String, Integer> scoreFunc) {
        return (s1, s2) -> Integer.compare(scoreFunc.apply(s2), scoreFunc.apply(s1));
    }

    public static void main5() {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(genComparator(scoreWithBonusFunc), words));
        System.out.println(rankedWords(genComparator(w -> scoreWithBonus(w)), words));
        System.out.println(rankedWords(genComparator(Cours2::scoreWithBonus), words));
    }
    */

    /* ====== SOLUTION 6 ====== */
    static Function<String, Integer> score = 
        s -> s.replaceAll("a", "").length();

    static Function<String, Integer> bonus = 
        s -> s.contains("c") ? 5 : 0;

    static Function<String, Integer> malus = 
        s -> s.contains("s") ? -7 : 0;

    static Function<String, Integer> calculator =
        s -> score.apply(s) + bonus.apply(s) + malus.apply(s);

    static Comparator<String> genComparator(Function<String, Integer> calculator) {
        return (l1, l2) -> Integer.compare(calculator.apply(l2), calculator.apply(l1));
    }

    static List<String> rankedWords(Comparator<String> comparator, List<String> words) {
        List<String> sortedWords = new ArrayList<>(words);
        sortedWords.sort(comparator);
        return sortedWords;
    }

    public static void main6() {
        List<String> listOfWords = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(genComparator(calculator), listOfWords));
        System.out.println(rankedWords(genComparator(w -> calculator.apply(w)), listOfWords));
    }

    public static void main(String[] args) {
        main6();
    }
}
