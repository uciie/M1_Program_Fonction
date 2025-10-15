package org.example.tds.td1;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.IntFunction;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import io.vavr.Tuple2;

public class Td1 {
    public record Information(String message, String country, LocalDateTime timestamp, List<String> tags, int price) {
    }

    public static Information parseInformation(String line) {
        String[] parts = line.split("--");
        String timestamp = parts[0].trim();
        String country = parts[1].trim();
        String message = parts[2].trim();
        List<String> tags = List.of(parts[3].split(",")).stream().map(String::trim).toList();
        int price = Integer.parseInt(parts[4].split("€")[0].trim());
        return new Information(message, country, LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME), tags, price);
    }

    public static List<LocalDateTime> informationTimestamps(List<Information> informations) {
        return informations.stream().map(Information::timestamp).toList();
    }

    public static List<Information> informationTag(String tag, List<Information> informations) {
        return informations.stream().filter(information -> information.tags.contains(tag)).toList();
    }

    public static List<Information> informationTagOneOf(List<String> tags, List<Information> informations) {
        return informations.stream()
                .filter(information -> information.tags.stream().anyMatch(tags::contains))
                .toList();
    }

    public static List<Information> informationMessageSuchThat(Predicate<String> pred, List<Information> informations) {
        return informations.stream()
                .filter(information -> pred.test(information.message())).toList();
    }

    public static List<Information> informationCountry(String country, List<Information> informations) {
        return informations.stream().filter(information -> information.country.equals(country)).toList();
    }

    // version initiale, mais on ne pourra pas appliquer partiellement
    // noter l'introduction du paramètre générique T
    public static <T> List<Information> selection(Function<Information, T> champ, Predicate<T> pred,
            List<Information> informations) {
        // noter qu'un map suivi d'un filter ne marche pas (on obtiendrait une liste de T et non d'informations)
        return informations.stream().filter(information -> pred.test(champ.apply(information))).toList();
    }

    // version modifiée pour permettre l'application partielle (1 coup)
    // essayez de donner les différents types "du cours" des différents cas pour selection
    public static <T> BiFunction<Predicate<T>, List<Information>, List<Information>> selection(Function<Information, T> champ) {
        return (pred, informations) -> selection(champ, pred, informations);
    }

    // version méthode, qui génère une fonction qu'on peut appliquer partiellement
    public static <T> Function<Function<Information, T>, Function<Predicate<T>, Function<List<Information>, List<Information>>>> selection() {
        return champ -> pred -> informations -> informations.stream()
                .filter(information -> pred.test(champ.apply(information))).toList();
    }

    public static <T, U> Tuple2<T, U> generePaire(IntFunction<T> f, IntFunction<U> g, int x) {
        return new Tuple2<>(f.apply(x), g.apply(x));
    }
    //
    // version avec selection
    //
    static BiFunction<Predicate<String>, List<Information>, List<Information>> messageSelecteur = selection(Information::message);
    static BiFunction<Predicate<List<String>>, List<Information>, List<Information>> tagsSelecteur = selection(Information::tags);
    static BiFunction<Predicate<String>, List<Information>, List<Information>> countrySelecteur = selection(Information::country);
    static BiFunction<Predicate<LocalDateTime>, List<Information>, List<Information>> timestampSelecteur = selection(Information::timestamp);
    

    public static void main(String[] args) {
        Function<Integer, Tuple2<Boolean, Boolean>> divisible2et3 = x -> generePaire(i -> i % 2 == 0, i -> i % 3 == 0,
                x);
        List.of(1, 2, 3, 4, 5, 6).stream().map(divisible2et3).forEach(System.out::println);
        //
        List<String> rawInformation = List.of(
                "2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€",
                "2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€",
                "2018-05-10T14:39:06 -- France -- This is a newer information  -- tag3 -- 8€");
        List<Information> information = rawInformation.stream().map(Td1::parseInformation).toList();

        //
        // versions directes
        //
        // liste des timestamps des informations
        System.out.println(informationTimestamps(information));
        // liste des informations contenant le mot "newer"
        System.out.println(informationMessageSuchThat(m -> m.contains("newer"), information));
        // liste des informations commençant par le mot "This"
        System.out.println(informationMessageSuchThat(m -> m.startsWith("This"), information));
        // liste des informations avec le tag "tag1"
        System.out.println(informationTag("tag1", information));
        // liste des informations avec le tag "tag2" ou "tag3"
        System.out.println(informationTagOneOf(List.of("tag2", "tag3"), information));
        // prix total des informations concernant la France
        System.out.println(informationCountry("France", information).stream().mapToInt(Information::price).sum());
        // ou
        System.out.println(
                informationCountry("France", information).stream().map(Information::price).reduce(0, (x, y) -> x + y));
        
        //
        // version avec selection
        //        
        System.out.println(messageSelecteur.apply(m -> m.contains("newer"), information));
        System.out.println(messageSelecteur.apply(m -> m.startsWith("This"), information));
        //
        System.out.println(tagsSelecteur.apply(ts -> ts.contains("tag1"), information));
        System.out.println(tagsSelecteur.apply(ts -> ts.stream().anyMatch(t -> List.of("tag2", "tag3").contains(t)), information));
        //
        System.out.println(countrySelecteur.apply(c -> c.equals("France"), information));
        //
        System.out.println(timestampSelecteur.apply(ts -> ts.getYear() == 2017, information));
    }
}