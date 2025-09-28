error id: file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/scala/src/main/scala/cours2/Td2.scala:java/lang/String#length().
file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/scala/src/main/scala/cours2/Td2.scala
empty definition using pc, found symbol in pc: java/lang/String#length().
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/Predef.
	 -scala/Predef#
	 -scala/Predef().
offset: 2358
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
    def score(word: String): Int = word.replaceAll("a", "").length@@
    def bonus(word: String): Int = if (word.contains("c")) 5 else 0
    def malus(word: String): Int = if (word.contains("s")) -7 else 0
    def totalScore(word: String): Int = score(word) + bonus(word) + malus(word)

    def highestScoringWord(words: List[String]): List[String] = {
        return words.filter(word => word.length > 1)
    }

    @main
    def wordScore_V4Main(): Unit = {
        val words = List("ada", "haskell", "scala", "java", "rust")
        println(highestScoringWord(words))
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#length().