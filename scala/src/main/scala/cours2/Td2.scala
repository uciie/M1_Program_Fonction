package cours2

object wordScore_V2 {
    def score(word: String): Int = {
        return word.replaceAll("a", "").length
    }

    def rankedWords(words: List[String]): List[String] = {
        return words.sortBy(word => -score(word)).reverse
    }

    @main
    def wordScore_V2Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        println(rankedWords(words))
    }
}

object wordScore_V3 {
    def score(word: String): Int = {
        return word.replaceAll("a", "").length
    }

    def bonus(word: String): Int = {
        return if (word.contains("c")) 5 else 0
    }

    def malus(word: String): Int = {
        return if (word.contains("s")) -7 else 0
    }

    def rankedWords(words: List[String]): List[String] = {
        return words.sortBy(word => score(word) + bonus(word) + malus(word)).reverse
    }

    @main
    def wordScore_V3Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        println(rankedWords(words))
    }
}

object wordScore_V4 {
    def score(word: String): Int = {
        return word.replaceAll("a", "").length
    }

    def bonus(word: String): Int = {
        return if (word.contains("c")) 5 else 0
    }

    def malus(word: String): Int = {
        return if (word.contains("s")) -7 else 0
    }

    def totalScore(word: String): Int = {
        return score(word) + bonus(word) + malus(word)
    }

    def rankedWords(words: List[String]): List[String] = {
        return words.map(word => (word, totalScore(word))).sortBy(_._2).reverse.map(_._1)
    }

    @main
    def wordScore_V4Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        println(rankedWords(words))
    }
}

object exercice1 {
    def longueurs(word: String): Int = return word.length

    def nbLettresS(word: String): Int = return word.count(_ == 's')

    def inverseNumber(n: Int): Int = return -n

    def double(x: Int): Int = return 2 * x

    @main
    def exercice1Main(): Unit = {
        assert(longueurs("scala") == 5)
        assert(nbLettresS("scala") == 1)
        assert(inverseNumber(1) == -1)
        assert(double(1) == 2)
    }
}

object wordScore_V5 {
    def score(word: String): Int = word.replaceAll("a", "").length
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0
    def totalScore(word: String): Int = score(word) + bonus(word) + malus(word)

    def highestScoringWord(scoring: String => Int, words: List[String], n: Int): List[String] = {
        return words.filter(word => scoring(word) > 1)
    }

    @main
    def wordScore_V5Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        assert(highestScoringWord(words => score(words), words, 1) == List("haskell", "scala", "java", "rust"))
        println(highestScoringWord(w => score(w) + bonus(w) - malus(w), words, 1))
    }
}

object wordScore_V6 {
    def score(word: String): Int = word.replaceAll("a", "").length
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0
    def totalScore(word: String): Int = score(word) + bonus(word) + malus(word)

    def highestScoringWord(scoring: String => Int, words: List[String], n: Int): List[String] = {
        return words.filter(word => scoring(word) > n)
    }

    @main
    def wordScore_V6Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")

        assert(highestScoringWord(totalScore, words, 1) == List("java"))
        assert(highestScoringWord(totalScore, words, 0) == List("ada", "scala", "java"))
        assert(highestScoringWord(totalScore, words, 5) == List())

        println(highestScoringWord(totalScore, words, 1))
        println(highestScoringWord(totalScore, words, 0))
        println(highestScoringWord(totalScore, words, 5))
    }
}

object exercice2 {
    def taille(words: List[String]): List[String] = {
        words.filter(word => word.length < 5)
        // words.filter(_.length > 5) // version plus concise
    }

    def plusUnL(words: List[String]): List[String] = {
        words.filter(word => word.contains("l"))
        // words.filter(_.contains("l")) // version plus concise
    }

    def nbPairs(numbers: List[Int]): List[Int] = {
        numbers.filter(number => number % 2 == 0)
        // numbers.filter(_ % 2 == 0) // version plus concise
    }

    def nbSup4(numbers: List[Int]): List[Int] = {
        numbers.filter(number => number > 4)
        // numbers.filter(_ > 4) // version plus concise
    }

    def tailleInf5(word: String): Boolean = {
        word.length < 5
    }

    def filterTailleInf5(words: List[String]): List[String] = {
        words.filter(tailleInf5)
    }

    def auMoinsUnL(word: String): Boolean = {
        word.contains("l")
    }

    def filterPlusUnL(words: List[String]): List[String] = {
        words.filter(auMoinsUnL)
    }

    def estPair(n: Int): Boolean = n % 2 == 0

    def filterPairs(numbers: List[Int]): List[Int] = {
        numbers.filter(estPair)
    }

    def estSup4(n: Int): Boolean = n > 4

    def filterSup4(numbers: List[Int]): List[Int] = {
        numbers.filter(estSup4)
    }

    // ========== METHODE DRY (Don't Repeat Yourself) ==========
    def filterWithPredicate[T](predicate: T => Boolean, list: List[T]): List[T] = {
        list.filter(predicate)
    }    

    @main
    def exercice2Main(): Unit = {
        assert(taille(List("scala", "java")) == List("java"))
        assert(plusUnL(List("scala", "haskell")) == List("scala", "haskell"))
        assert(nbPairs(List(1, 2, 3, 4)) == List(2, 4))
        assert(nbSup4(List(1, 2, 3, 4, 5)) == List(5))

        assert(filterWithPredicate(tailleInf5, List("scala", "java")) == List("java"))
        assert(filterWithPredicate(auMoinsUnL, List("scala", "haskell")) == List("scala", "haskell"))
        assert(filterWithPredicate(estPair, List(1, 2, 3, 4)) == List(2, 4))
        assert(filterWithPredicate(estSup4, List(1, 2, 3, 4, 5)) == List(5))
    }
}

object wordScore_V6_Ameliore {
    def score(word: String): Int = word.replaceAll("a", "").length
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0

    def highestScoringWord2(scoring: String => Int): (List[String], Int) => List[String] = {
        (words: List[String], n: Int) => words.filter(word => scoring(word) > n)
    }

    @main
    def wordScore_V6_AmelioreMain(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        val highScoringSBM = highestScoringWord2(w => score(w) + bonus(w) - malus(w))
        println(highScoringSBM(words, 1))
        println(highScoringSBM(words, 0))
        println(highScoringSBM(words, 5))

        assert(highScoringSBM(words, 1) == List("haskell", "scala", "java", "rust"))
        assert(highScoringSBM(words, 0) == List("ada", "haskell", "scala", "java", "rust"))
        assert(highScoringSBM(words, 5) == List("haskell", "scala", "rust"))
    }
}

object exercice3 {
    // == nombres supérieurs à 4, puis 5 ==
    def filterSup4(numbers: List[Int]): List[Int] = {
        numbers.filter(_ > 4)
    }

    def filterSup(borne: Integer, numbers: List[Int]): List[Int] = {
        numbers.filter(_ > borne)
    }

    def filterSupCurry(borne: Integer): (List[Integer] => List[Integer]) = {
        numbers => numbers.filter(_ > borne)
    }

    // == nombres divisibles par 5, puis par 2 ==
    def filterDivCurry(diviseur: Integer): (List[Integer] => List[Integer]) = {
        numbers => numbers.filter(_ % diviseur == 0)
    }

    // == mots de taille < 4, puis < 7 ==
    def filterTailleInfCurry(borne: Integer): (List[String] => List[String]) = {
        words => words.filter(_.length < borne)
    }

    // == mots contenant plus de deux ‘s’, puis un == 
    def filterContientPlusieurCurry(n: Int)(chain: Char)(words: List[String]): List[String] =
        words.filter(word => word.count(_ == chain) > n)


    @main
    def exercice3Main(): Unit = {
        val filterSupCurry4 = filterSupCurry(4)
        val filterSupCurry5 = filterSupCurry(5)
        val filterDivCurry5 = filterDivCurry(5)
        val filterDivCurry2 = filterDivCurry(2)

        val f1: Char => List[String] => List[String] = filterContientPlusieurCurry(2)
        val f2: List[String] => List[String] = f1('s')
        val f3: List[String] = f2(List("scala", "haskell", "rust", "ssshock"))

        println(filterSup4(List(1, 2, 3, 4, 5, 6)))
        println(filterSupCurry4(List(1, 2, 3, 4, 5, 6)))
        println(filterSupCurry5(List(1, 2, 3, 4, 5, 6)))
        println(filterDivCurry5(List(1, 2, 3, 4, 5, 6)))
        println(filterDivCurry2(List(1, 2, 3, 4, 5, 6)))
    }
}

object curryficationMethodes{
    def addition(a: Int, b: Int): Int = a + b

    def additionCurry(a: Int): Int => Int = b => a + b

    @main
    def curryficationMethodesMain(): Unit = {
        val add2 = additionCurry(2)
        val v2 = add2(3) // 5
        val v3 = additionCurry(2)(3) // 5
        println(add2)
        println(v2)
        println(v3)
    }
}

object wordScore_V7 {
    /* Explication du concept : FoldLeft et FoldRight
        val numbers = List(1, 2, 3, 4, 5)

        // Somme avec foldLeft
        val sumLeft = numbers.foldLeft(0)((acc, x) => acc + x)
        println(sumLeft) // 15
        
        // Trace du déroulement : (((((0 + 1) + 2) + 3) + 4) + 5)
        
        // Somme avec foldRight
        val sumRight = numbers.foldRight(0)((x, acc) => x + acc)   
        println(sumRight) // 15

        // Trace du déroulement : (1 + (2 + (3 + (4 + (5 + 0)))))  
    */

    def score(word: String): Int = word.replaceAll("a", "").length
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0

    def highestScoringWordCurried(scoring: String => Int)(words: List[String], n: Int): List[String] = {
        return words.filter(word => scoring(word) > n)
    }

    def cumulativeScore(scoring: String => Int)(words: List[String]): Int = {
        words.foldLeft(0)((acc, word) => acc + scoring(word))
    }

    @main
    def wordScore_V7Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        val cumulativeScoreSBM = cumulativeScore(w => score(w) + bonus(w) - malus(w))(words)
        println("Cumulative score: " + cumulativeScoreSBM)

        println(cumulativeScoreSBM)
    }
}

object exercice4{
    def sumIntList(nbs: List[Int]): Int = {
        nbs.foldLeft(0)((acc, n) => acc + n)
    }

    def lengthTotal(words: List[String]): Int = {
        words.foldLeft(0)((acc, word) => acc + word.length)
    }

    def nbTotalChar(c: Char)(words: List[String]): Int = {
        words.foldLeft(0)((acc, word) => acc + word.count(_ == c))
    }

    def maxList(nbs: List[Int]): Int = {
        nbs.foldLeft(Int.MinValue)((acc, n) => if (n > acc) n else acc)
        //nbs.foldLeft(Int.MinValue)((acc, n) => acc.max(n)) // version plus concise
    }

    @main
    def exercice4Main(): Unit = {
        assert(sumIntList(List(1, 2, 3, 4)) == 10)
        assert(lengthTotal(List("scala", "java")) == 9)
        assert(nbTotalChar('s')(List("scala", "haskell", "rust")) == 3)
        assert(maxList(List(1, 2, 3, 4, 0, -1)) == 4)
    }
}

object exercice5{
    case class ProgrammingLanguage(name: String, year: Int)

    @main
    def exercice5Main(): Unit = {
        val java = ProgrammingLanguage("Java", 1995)
        val scala = ProgrammingLanguage("Scala", 2004)
        val haskell = ProgrammingLanguage("Haskell", 1990)
        val python = ProgrammingLanguage("Python", 1991)
        val languages = List(java, scala, haskell, python)

        val listeNames = languages.map(_.name)
        println("La liste des noms:\n" + listeNames+"\n")

        val listeYearsPost2000 = languages.filter(_.year >= 2000)
        println("La liste des années post-2000:\n" + listeYearsPost2000)
    }
}