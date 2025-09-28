error id: file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/scala/src/main/scala/cours2/Td2.scala:scala/package.List.
file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/scala/src/main/scala/cours2/Td2.scala
empty definition using pc, found symbol in pc: scala/package.List.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -List.
	 -List#
	 -List().
	 -scala/Predef.List.
	 -scala/Predef.List#
	 -scala/Predef.List().
offset: 3049
uri: file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/scala/src/main/scala/cours2/Td2.scala
text:
```scala
package cours2

object wordScore_V1 {
    def score(word: String): Int = {
        return word.replaceAll("a", "").length
    }

    def rankedWords(words: List[String]): List[String] = {
        return words.sortBy(word => -score(word)).reverse
    }

    @main
    def wordScore_V1Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        println(rankedWords(words))
    }
}

object wordScore_V2 {
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

    def totalScore(word: String): Int = {
        return score(word) + bonus(word) + malus(word)
    }

    def rankedWords(words: List[String]): List[String] = {
        return words.map(word => (word, totalScore(word))).sortBy(_._2).reverse.map(_._1)
    }

    @main
    def wordScore_V3Main(): Unit = {
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

object wordScore_V4 {
    def score(word: String): Int = word.replaceAll("a", "").length
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0
    def totalScore(word: String): Int = score(word) + bonus(word) + malus(word)

    def highestScoringWord(scoring: String => Int, words: List[String], n: Int): List[String] = {
        return words.filter(word => scoring(word) > n)
    }

    @main
    def wordScore_V4Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")

        assert(highestScoringWord(words => score(words), words, 1) == List("haskell", "scala", "java", "rust"))
        assert(highestScoringWord(words => score(words), words, 0) == @@List("ada", "haskell", "scala", "java", "rust"))
        assert(highestScoringWord(words => score(words), words, 5) == List("haskell", "scala", "java"))

        println(highestScoringWord(w => score(w) + bonus(w) - malus(w), words, 1))
        println(highestScoringWord(w => score(w) + bonus(w) - malus(w), words, 0))
        println(highestScoringWord(w => score(w) + bonus(w) - malus(w), words, 5))
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/package.List.