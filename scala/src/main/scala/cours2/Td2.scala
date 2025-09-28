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

object exercice {
    def longueurs(word: String): Int = return word.length

    def nbLettresS(word: String): Int = return word.count(_ == 's')

    def inverseNumber(n: Int): Int = return -n

    def double(x: Int): Int = return 2 * x

    @main
    def exerciceMain(): Unit = {
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