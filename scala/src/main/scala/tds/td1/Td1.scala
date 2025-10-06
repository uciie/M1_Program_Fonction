package tds.td1

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Td1 {
    case class Information(message: String, country: String, timestamp: LocalDateTime, tags: List[String], price: Int)

    def parseInformation(line: String): Information = {
        val parts = line.split("--")
        val timestamp = parts(0).trim
        val country = parts(1).trim
        val message = parts(2).trim
        val tags = parts(3).split(",").map(_.trim).toList
        val price = parts(4).split("€")(0).trim.toInt
        Information(message, country, LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME), tags, price)
    }

    // ==================== Partie 1.a ====================

    // avoir la liste des timestamps des info
    def informationTimestamps(infos: List[Information]): List[LocalDateTime] = {
        infos.map(_.timestamp)
    }

    // connaître les info contenant un tag
    def informationTag(tag: String)(infos: List[Information]): List[Information] = {
        infos.filter(_.tags.contains(tag))
    }

    // connaître les info contenant un ou plusieurs tags pris dans une liste
    def informationWithTags(tags: List[String])(infos: List[Information]): List[Information] = {
        infos.filter(info => info.tags.exists(tag => tags.contains(tag)))
        // infos.filter(info => info.tags.intersect(tags).nonEmpty)
    }

    // connaître les info qui satisfont une condition donnée sur le msg
    def informationMessageSuchThat(condition: String => Boolean)(infos: List[Information]): List[Information] = {
        infos.filter(info => condition(info.message))
    }

    // connaître les info d'un pays donné
    def informationCountry(country: String)(infos: List[Information]): List[Information] = {
        infos.filter(_.country == country)
    }

    // ==================== Partie 1.b ====================
    def selection[A](selectField: Information => A)(condition: A => Boolean)(infos: List[Information]): List[Information] = {
        infos.filter(info => condition(selectField(info)))
    }

    val messageSelecteur = selection(_.message)(_: String => Boolean)
    val tagsSelecteur = selection(_.tags)(_: List[String] => Boolean)
    val countrySelecteur = selection(_.country)(_: String => Boolean)
    val timestampSelecteur = selection(_.timestamp)(_: LocalDateTime => Boolean)

    // ==================== Tests ====================
    def test_1a(information: List[Information]): Unit = {
        assert(informationTimestamps(information) == List(
            LocalDateTime.parse("2017-05-08T14:39:06"),
            LocalDateTime.parse("2017-05-08T14:49:06"),
            LocalDateTime.parse("2018-05-10T14:39:06")
        ))

        assert(informationMessageSuchThat(msg => msg.contains("newer"), information) == List(
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))

        assert(informationMessageSuchThat(msg => msg.startsWith("This"), information) == information)

        assert(informationTag("tag1")(information) == List(
            parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
            parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€")
        ))

        assert(informationWithTags(List("tag2", "tag3"))(information) == List(
            parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€"),
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))

        assert(informationCountry("France")(information) == List(
            parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))

        println("Test 1a passed")
    }

    def test_1b(informations: List[Information]): Unit = {
        assert(messageSelecteur(msg => msg.contains("newer"))(informations) == List(
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))

        assert(messageSelecteur(msg => msg.startsWith("This"))(informations) == informations)

        assert(tagsSelecteur(tags => tags.contains("tag1"))(informations) == List(
            parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
            parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€")
        ))

        assert(tagsSelecteur(tags => tags.exists(tag => List("tag2", "tag3").contains(tag)))(informations) == List(
            parseInformation("2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€"),
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))

        assert(countrySelecteur(country => country == "France")(informations) == List(
            parseInformation("2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€"),
            parseInformation("2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€")
        ))
        println("Test 1b passed")
    }

    @main
    def test(): Unit = {
        val rawInformation = List(
            "2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€",
            "2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€",
            "2018-05-10T14:39:06 -- France -- This is a newer information -- tag3 -- 8€"
        )
        val information = rawInformation.map(parseInformation(_))
        test_1a(information)
        test_1b(information)
    }
}