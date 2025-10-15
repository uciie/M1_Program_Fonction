package tds.td1

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

object Td1 {

  // objectifs :
  // étude de cas simple utilisant définition de fonctions, ordre supérieur, application partielle,
  // sortBy, map et filter (pas encore de flatMap à cette étape)

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

  def informationTimestamps(informations: List[Information]): List[LocalDateTime] = {
    informations.map(_.timestamp)
  }
  
  def informationTag(tag: String)(informations: List[Information]): List[Information] = {
    informations.filter(_.tags.contains(tag))
  }

  def informationTagOneOf(tags: List[String])(informations: List[Information]): List[Information] = {
    informations.filter(_.tags.intersect(tags).nonEmpty)
  }

  def informationMessageSuchThat(pred: String => Boolean)(informations: List[Information]): List[Information] = {
    informations.filter(i => pred(i.message))
  }

  def informationCountry(country: String)(informations: List[Information]): List[Information] = {
    informations.filter(_.country == country)
  }

  def selection[T](x: Information => T)(y: T => Boolean)(is: List[Information]): List[Information] = {
    is.filter(x.andThen(y)(_))
  }

  def génèrePaire[T,U](f: Int => T)(g: Int => U)(x: Int): (T,U) = (f(x), g(x))

  @main
  def test(): Unit = {
    val divisible2et3 = génèrePaire(i => i%2 == 0)(i => i%3 == 0)
    List(1,2,3,4,5,6).map(divisible2et3).foreach(println)
    //
    val rawInformation = List(
      "2017-05-08T14:39:06 -- France -- This is an information -- tag1 -- 2€",
      "2017-05-08T14:49:06 -- UK -- This is another information -- tag1,tag2 -- 4€",
      "2018-05-10T14:39:06 -- France -- This is a newer information  -- tag3 -- 8€"
    )
    val information = rawInformation.map(parseInformation(_))
    //
    // version directes
    //
    // liste des timestamps des informations
    println(information.map(_.timestamp))
    // liste des informations contenant le mot "newer"
    println(informationMessageSuchThat(_.contains("newer"))(information))
    // liste des informations commençant par le mot "This"
    println(informationMessageSuchThat(_.startsWith("This"))(information))
    // liste des informations avec le tag "tag1"
    println(informationTag("tag1")(information))
    // liste des informations avec le tag "tag2" ou "tag3"
    println(informationTagOneOf(List("tag2", "tag3"))(information))
    // prix total des informations concernant la France
    println(informationCountry("France")(information).map(_.price).sum)
    println(informationCountry("France")(information).map(_.price).foldLeft(0, (x: Int, y: Int) => x + y))
    //
    // versions avec selection
    //
    val messageSelecteur = selection(i => i.message)
    val tagSelecteur = selection(_.tags)
    val countrySelecteur = selection(_.country)
    val timestampSelecteur = selection(_.timestamp)
    println(messageSelecteur(_.contains("newer"))(information))
    println(messageSelecteur(_.startsWith("This"))(information))
    println(tagSelecteur(_.contains("tag1"))(information))
    println(tagSelecteur(_.intersect(List("tag2", "tag3")).nonEmpty)(information))
    println(countrySelecteur(_ == "France")(information))
    println(timestampSelecteur(_.getYear() == 2017)(information))
  }

}
