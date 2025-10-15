package cours.cours5

object Cours5 {
    case class Book(title: String, authors: List[String])
    val books = List(  
        Book("FP in Scala", List("Chiusano", "Bjarnason")),  
        Book("The Hobbit", List("Tolkien")),  
        Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft"))
        )

    def nbHaveName(pattern: String)(books: List[Book]): Int = 
        books.map(_.title)
                .filter(_.contains(pattern))
                .size

    case class Movie(title: String)
    def bookAdaptations(author: String): List[Movie] = {
        if (author == "Tolkien")
            List(Movie("An Unexpected Journey"), Movie("The Desolation of Smaug"))
        else
            List.empty
    }

    def listAuthors(books: List[Book]): List[String] = 
        books.map(_.authors).flatten
        // books.flatMap(_.authors)

    def recommendations(books: List[Book]): List[Movie] = 
        books.flatMap(_.authors).flatMap(bookAdaptations)
        // car bookAdaptations renvoie une liste de films

    @main
    def main(): Unit = {
        val result1 = List(1, 2, 3).flatMap(i => List(i, i + 10))
        val result2 = List(1, 2, 3).flatMap(i => List(i * 2))
        val result3 = List(1, 2, 3).flatMap(i => if (i % 2 == 0) List(i) else List.empty)
        assert(result1 == List(1, 11, 2, 12, 3, 13))
        assert(result2 == List(2, 4, 6))
        assert(result3 == List(2))
    }
}

object exerciceFlatMap {
    case class Book(title: String, authors: List[String])

    def recommendedBy(friend: String): List[Book] = 
        if (friend == "Alice")
            List(Book("The Hobbit", List("Tolkien")), Book("FP in Scala", List("Chiusano", "Bjarnason")))
        else if (friend == "Bob")
            List(Book("Effective Java", List("Bloch")), Book("Clean Code", List("Martin")))
        else
            List.empty
    
    val friends = List("Alice", "Bob", "Charlie")

    val recommendedBooks: List[Book] = 
        friends.flatMap(recommendedBy)

    val recommendedAuthors: List[String] = 
        recommendedBooks.flatMap(_.authors)

    case class Point3D(x: Int, y: Int, z: Int)
    val points3D: List[Point3D] = 
        (for {
            x <- List(1, -1)
            y <- List(1, 0, -1 )
            z <- List(1,-1,1)
        } yield Point3D(x, y, z))
    
    @main
    def testExerciceFlatMap(): Unit = {
        assert(points3D.size == 18)
        println(points3D)
    }
    
}

object cerclesV1{
    case class Point(x: Int, y: Int)
    def isInside(point: Point, radius: Int): Boolean = {    
        radius * radius >= point.x * point.x + point.y * point.y
    }
    val points = List(Point(5,2), Point(1,1))
    val radiuses = List(2, 1)

    // solution 1
    def checkInside1(points: List[Point], radiuses: List[Int]): List[Point] = {
        for {
            point <- points
            radius <- radiuses
            if isInside(point, radius)
        } yield point
    }
    // solution 2
    def insideFilter(point: Point, radius: Int): List[Point] = {    
        if isInside(point, radius) then List(point) else List.empty
    }

    def checkInside2(points: List[Point], radiuses: List[Int]): List[Point] = {
        for {
            point <- points
            radius <- radiuses
            point <- insideFilter(point, radius)
        } yield point
    }
    
    // solution 3
    def checkInside3(points: List[Point], radiuses: List[Int]): List[Point] = {
        for {
            radius <- radiuses
            point <- points.filter(isInside(_, radius))
        } yield point
    }

    
}

object cercles{
    // on souhaite éviter les données invalides
    case class Point(x: Int, y: Int)
    val points = List(Point(5, 2), Point(1, 1))
    val radiuses = List(-10, 0, 2)    
}