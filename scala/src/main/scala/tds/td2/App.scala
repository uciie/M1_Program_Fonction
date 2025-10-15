package tds.td2

import tds.td2.modele.{Annee, Matiere, UE, Etudiant}

object App:

    // 2.1
    def afficheSi(entete: String, pred: Etudiant => Boolean, annee: Annee): Unit =
        val result = "**" + entete + "\n" +
            annee.etudiants.filter(pred).mkString("\n")
        println(result)

    // 2.2
    def aDef(etudiant: Etudiant): Boolean = 
        etudiant.annee().ues.flatMap(_.ects.keySet)
            .exists(matiere => etudiant.notes().get(matiere).isEmpty)

    // 2.3
    def aNoteEliminatoire(etudiant: Etudiant): Boolean =
        etudiant.notes().values.exists(_ < 6)

    // 2.4
    def moyenne(etudiant: Etudiant): Option[Double] =
        if aDef(etudiant)
            then None
            else Some(moyenneNonDefaillant(etudiant))

    private def moyenneNonDefaillant(etudiant: Etudiant): Double =
        // on parcoure deux fois, pas efficace, on verra dans la suite du TD
        // on utilise getOrElse avec 0.0 car on sait que l'étudiant n'est pas défaillant
        // note: on peut utiliser un for ici (cf flatMap + map)
        val sommePonderee = etudiant.annee().ues.flatMap(ue => ue.ects.map(t => etudiant.notes().getOrElse(t._1, 0.0) * t._2)).sum
        // attention toList nécessaire car plusieurs coefficients identiques
        val sommeCoefficients = etudiant.annee().ues.toList.flatMap(_.ects.values).sum
        sommePonderee / sommeCoefficients

    // 2.5
    // ici on est "obligés" de faire "propre" (donc se déporte un peu du sujet Java avec son null)
    def naPasLaMoyennev1(etudiant: Etudiant): Boolean =
        moyenne(etudiant).isEmpty || moyenne(etudiant).get < 10

    // 2.6
    def naPasLaMoyennev2 = naPasLaMoyennev1

    // 2.7
    // ici la question ne fait pas de sens vu la définition en 2.5
    def session2v1(etudiant: Etudiant): Boolean = 
        aDef(etudiant) || naPasLaMoyennev1(etudiant) || aNoteEliminatoire(etudiant)

    // 2.8
    def afficheSiv2(repr: Etudiant => String)(entete: String, pred: Etudiant => Boolean, annee: Annee): Unit =
        val result = "**" + entete + "\n" +
            annee.etudiants.filter(pred).map(repr).mkString("\n")
        println(result)

    // 2.9
    def moyenneIndicative(etudiant: Etudiant): Double =
        // voir commentaires réponse 2.4
        // ici on a déjà prévu le 0.0
        val sommePonderee = etudiant.annee().ues.flatMap(ue => ue.ects.map(t => etudiant.notes().getOrElse(t._1, 0.0) * t._2)).sum
        val sommeCoefficients = etudiant.annee().ues.toList.flatMap(_.ects.values).sum
        sommePonderee / sommeCoefficients

    // 2.10
    def naPasLaMoyenneGeneralisee(moyenne: Etudiant => Option[Double])(etudiant: Etudiant): Boolean =
        moyenne(etudiant).isEmpty || moyenne(etudiant).get < 10

    // pour la question 3 on utilise des versions f: A => B = ...
    // mais on pourrait faire aussi des versions f(a:A): B = ...

    // 3.1
    // public static final Function<Annee, Stream<Matiere>> matieresA = ???
    // en Scala on a des Streams (pas vus en cours contrairement à ceux de Java)
    // on peut passer par un Set
    def matieresA: Annee => Set[Matiere] = _.ues.flatMap(_.ects.keySet)

    // 3.2
    // public static final Function<Etudiant, Stream<Matiere>> matieresE = ???
    def matieresE: Etudiant => Set[Matiere] = e => matieresA(e.annee())

    // 3.3
    // public static final Function<Etudiant, Stream<Entry<Matiere, Integer>>> matieresCoefE_ = ???
    // penser à passer en liste avant de faire flatMap
    def matieresCoefEraw: Etudiant => List[(Matiere, Int)] = 
        _.annee().ues.toList.flatMap(_.ects)

    // 3.4
    // public static final Function<Entry<Matiere, Integer>, Paire<Matiere, Integer>> entry2paire = ???
    // inutile en Scala
    def entry2paire: ((Matiere, Int)) => (Matiere, Int) = identity

    // 3.5
    // public static final Function<Etudiant, Stream<Paire<Matiere, Integer>>> matieresCoefE = ???
    // inutile en Scala
    def matieresCoefE: Etudiant => List[(Matiere, Int)] = matieresCoefEraw
    def matieresCoefEv2: Etudiant => List[(Matiere, Int)] = matieresCoefEraw.andThen(_.map(entry2paire))

    // 3.6
    // public static final BinaryOperator<Paire<Double, Integer>> accumulateurMoyenne = ???
    def accumulateurMoyenne(acc: (Double, Int), other: (Double, Int)): (Double, Int) = 
        (acc._1 + other._1*other._2, acc._2 + other._2)
    def accumulateurMoyennev2: ((Double, Int), (Double, Int)) => (Double, Int) = 
        (acc, other) => (acc._1 + other._1*other._2, acc._2 + other._2)

    // 3.7
    // public static final Paire<Double, Integer> zero = ???
    val zero: (Double, Int) = (0.0, 0)

    // 3.8
    // public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPonderees = ???
    // en Scala get donne un Option donc coincide avec 3.9

    // 3.9
    // public static final Function<Etudiant, List<Paire<Double, Integer>>> notesPondereesIndicatives = ???    
    // exemple de pattern matching sur tuple (évite les _1 et _2)
    def notesPondereesIndicatives(e: Etudiant): List[(Double, Int)] =
        matieresCoefE(e).map({case (mat, coef) => (e.notes().getOrElse(mat, 0.0), coef)})

    // 3.10
    // public static final Function<List<Paire<Double, Integer>>, Paire<Double, Integer>> reduit = ???
    def reduit: List[(Double, Int)] => (Double, Int) =
        _.foldLeft(zero)(accumulateurMoyenne)

    // 3.11
    // public static final Function<Paire<Double, Integer>, Double> divise = ???
    def divise: ((Double, Int)) => Double = 
        {case (sommePonderee, sommeCoefs) => sommePonderee / sommeCoefs}

    // 3.12 et 3.13 (même commentaire de 3.8/3.9)
    // public static final Function<Etudiant, Double> computeMoyenne = ???
    def computeMoyenne: Etudiant => Double = 
        notesPondereesIndicatives.andThen(reduit).andThen(divise)

    @main
    def main(): Unit =
        val m1 = Matiere("MAT1")
        val m2 = Matiere("MAT2")
        val m3 = Matiere("MAT3")
        val ue1 = UE("UE1", Map(m1 -> 2, m2 -> 2))
        val ue2 = UE("UE2", Map(m3 -> 1))
        val annee = Annee(Set(ue1, ue2))
        val e1 = Etudiant("39001", "Alice", "Merveille")
        val e2 = Etudiant("39002", "Bob", "Eponge")
        val e3 = Etudiant("39003", "Charles", "Chaplin")
        annee.inscrire(e1)
        annee.inscrire(e2)
        annee.inscrire(e3)
        // e1.inscrire(annee) // impossible grâce à private[model]
        e1.noter(m1, 12)
        e1.noter(m2, 14)
        e1.noter(m3, 10)
        e2.noter(m1, 14)
        e2.noter(m3, 14)
        e3.noter(m1, 18)
        e3.noter(m2, 5)
        e3.noter(m3, 14)
        println(e1)
        // 2.1
        afficheSi("TOUS LES ETUDIANTS", _ => true, annee)
        // 2.2
        afficheSi("ETUDIANTS DEFAILLANTS", aDef, annee)
        // 2.3
        afficheSi("ETUDIANTS AVEC NOTE ELIMINATOIRE", aNoteEliminatoire, annee)
        // 2.4
        List(e1, e2, e3).foreach(e => println(moyenne(e)))
        // 2.5
        afficheSi("ETUDIANTS SOUS LA MOYENNE (v1)", naPasLaMoyennev1, annee)
        // 2.6
        afficheSi("ETUDIANTS SOUS LA MOYENNE (v2)", naPasLaMoyennev2, annee)
        // 2.7
        afficheSi("ETUDIANTS EN SESSION 2 (v1)", session2v1, annee)
        // 2.8
        afficheSiv2(_.toString)("TOUS LES ETUDIANTS", _ => true, annee)
        afficheSiv2(e => s"${e.prenom} ${e.nom} : ${moyenne(e).getOrElse("défaillant")}")("TOUS LES ETUDIANTS (AVEC MOYENNE)", _ => true, annee)
        // 2.9
        afficheSiv2(e => s"${e.prenom} ${e.nom} : ${moyenneIndicative(e)}")("TOUS LES ETUDIANTS (AVEC MOYENNE)", _ => true, annee)
        // 2.10
        afficheSiv2(e => s"${e.prenom} ${e.nom} : ${moyenneIndicative(e)}")("ETUDIANTS SOUS LA MOYENNE (généralisée et AVEC MOYENNE)", naPasLaMoyenneGeneralisee(moyenneIndicative.andThen(Some.apply)), annee)
        e2.noter(m1, 20)
        e2.noter(m3, 20)
        afficheSiv2(e => s"${e.prenom} ${e.nom} : ${moyenneIndicative(e)}")("ETUDIANTS SOUS LA MOYENNE (généralisée et AVEC MOYENNE)", naPasLaMoyenneGeneralisee(moyenneIndicative.andThen(Some.apply)), annee)
        // 3
        e2.noter(m1, 14)
        e2.noter(m3, 14)
        println(notesPondereesIndicatives(e3))
        List(e1, e2, e3).map(computeMoyenne).foreach(println(_))


