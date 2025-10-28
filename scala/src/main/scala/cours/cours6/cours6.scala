package cours.cours6

object cours6 {
    case class Event(name: String, start: Int, end: Int)

    def parse(name: String, start: Int, end: Int): Event =  
        if (name.size > 0 && end < 3000 && start <= end)
            Event(name, start, end)
        else
            null

    def parseV2(name: String, start: Int, end: Int): Option[Event] =  
        if (name.size > 0 && end < 3000 && start <= end)
            Some(Event(name, start, end))
        else
            None

    @main def mainCours6(): Unit = {
        val e1 = parse("Event 1", 1000, 1200)
        println(e1)

        val e2 = parse("", 1000, 1200).name // NullPointerException
        println(e2)

        val ev1 = parseV2("Event 1", 1000, 1200)
        println(ev1)

        val ev2 = parseV2("", 1000, 1200).name 
        println(ev2)
    }
}