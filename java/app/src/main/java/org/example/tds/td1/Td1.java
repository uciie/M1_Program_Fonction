package org.example.tds.td1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.function.Predicate;

class Information {
    public final LocalDateTime timestamp;
    public final String message;
    public final String country;
    public final List<String> tags;
    public final int price;

    public Information(String message, LocalDateTime timestamp, String country, List<String> tags, int price) {
        this.message = message;
        this.timestamp = timestamp;
        this.country = country;
        this.tags = tags;
        this.price = price;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }
    public String message() {
        return message;
    }
    public String country() {
        return country;
    }
    public List<String> tags() {
        return tags;
    }
    public int price() {
        return price;
    }
}

public class Td1{
    static Information parseInformation(String s) {
        String[] parts = s.split(" -- ");
        LocalDateTime timestamp = LocalDateTime.parse(parts[0].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String country = parts[1].trim();
        String message = parts[2].trim();
        List<String> tags = Arrays.stream(parts[3].split(",")).map(String::trim).toList();
        int price = Integer.parseInt(parts[4].replace("€", "").trim());
        return new Information(message, timestamp, country, tags, price);
    }

    // ==================== Partie 1.a ====================

    // avoir la liste des timestamps des info
    static List<LocalDateTime> informationTimestamps(List<Information> infos) {
        return infos.stream()
                .map(info -> info.timestamp) 
                // .map(Information::timestamp)
                .toList();
    }

    // connaître les info contenant un tag
    static List<Information> informationTag(String tag, List<Information> infos) {
        return infos.stream()
                .filter(info -> info.tags().contains(tag))
                // .filter(Information::tags().contains(tag))
                .toList();
    }

    // connaître les info contenant un ou plusieurs tags pris dans une liste
    static List<Information> informationWithTags(List<String> tags, List<Information> infos){
        return infos.stream()
                .filter(info -> info.tags().stream().anyMatch(tags::contains))
                // .filter(Information::tag.equalsAny(tags))
                .toList();
    }

    // connaître les info qui satisfont une condition donnée sur le msg
    static List<Information> informationMessagesSuchThat(Predicate<String> condition, List<Information> infos){
        return infos.stream()
                .filter(info -> condition.test(info.message))
                // .filter(Information::message.satisfies(condition))
                .toList();
    }

    // connaître les info d'un pays donné
    static List<Information> informationCountry(String country, List<Information> infos){
        return infos.stream()
                .filter(info -> info.country.equals(country))
                .toList();
    }


    public static void main(String[] args) {
    }
}