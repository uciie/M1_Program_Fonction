package org.example.tds.td1;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

public class Td1Test {

    private final List<String> rawInformation = List.of(
            "2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€",
            "2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€",
            "2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€"
    );

    private final List<Td1.Information> informations = rawInformation.stream()
            .map(Td1::parseInformation)
            .toList();

    // ==================== Partie 1.a ====================
    @Test
    public void testInformationTimestamps() {
        List<LocalDateTime> expected = List.of(
                LocalDateTime.parse("2017-05-08T14:39:06"),
                LocalDateTime.parse("2017-05-08T14:49:06"),
                LocalDateTime.parse("2018-05-10T14:39:06")
        );
        assertEquals(expected, Td1.informationTimestamps(informations));
    }

    @Test
    public void testInformationMessageSuchThat() {
        List<Td1.Information> expectedNewer = List.of(
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        System.out.println(expectedNewer);
        System.out.println(Td1.informationMessageSuchThat(msg -> msg.contains("newer"), informations));
        assertEquals(expectedNewer, Td1.informationMessageSuchThat(msg -> msg.contains("newer"), informations));

        assertEquals(informations,Td1.informationMessageSuchThat(msg -> msg.startsWith("This"), informations));
    }

    @Test
    public void testInformationTag() {
        List<Td1.Information> expected = List.of(
                Td1.parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
                Td1.parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€")
        );
        assertEquals(expected, Td1.informationTag("tag1", informations));
    }

    @Test
    public void testInformationTagOneOf() {
        List<Td1.Information> expected = List.of(
                Td1.parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€"),
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        assertEquals(expected, Td1.informationTagOneOf(List.of("tag2", "tag3"), informations));
    }

    @Test
    public void testInformationCountry() {
        List<Td1.Information> expected = List.of(
                Td1.parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        assertEquals(expected, Td1.informationCountry("France", informations));
    }

    // ==================== Partie 1.b ====================
    @Test
    public void testSelectionMessageSelecteur() {
        List<Td1.Information> expectedNewer = List.of(
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        assertEquals(expectedNewer,
                Td1.messageSelecteur.apply(msg -> msg.contains("newer"), informations));

        assertEquals(informations,
                Td1.messageSelecteur.apply(msg -> msg.startsWith("This"), informations));
    }

    @Test
    public void testSelectionTagsSelecteur() {
        List<Td1.Information> expectedTag1 = List.of(
                Td1.parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
                Td1.parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€")
        );
        assertEquals(expectedTag1,
                Td1.tagsSelecteur.apply(tags -> tags.contains("tag1"), informations));

        List<Td1.Information> expectedTag2Tag3 = List.of(
                Td1.parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€"),
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        assertEquals(expectedTag2Tag3,
                Td1.tagsSelecteur.apply(tags -> tags.stream().anyMatch(t -> List.of("tag2", "tag3").contains(t)), informations));
    }

    @Test
    public void testSelectionCountrySelecteur() {
        List<Td1.Information> expected = List.of(
                Td1.parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
                Td1.parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        );
        assertEquals(expected,
                Td1.countrySelecteur.apply(c -> c.equals("France"), informations));
    }
}
