package tds.td2.modele

// possible fuite de données + non fonctionnel
// modif modèle pour cohérence inscriptions
final case class Etudiant(numero: String, prenom: String, nom: String):

    private var _notes: Map[Matiere, Double] = Map()
    private var _annee: Annee = null.asInstanceOf[Annee]

    def notes(): Map[Matiere, Double] = _notes

    def annee(): Annee = _annee

    private[modele] def inscrire(anneeInscription: Annee): Unit =
        _annee = anneeInscription

    def noter(matiere: Matiere, note: Double): Unit =
        _notes = _notes.updated(matiere, note)

    override def toString(): String =
        s"$numero $prenom $nom" + "\n" +
        annee().ues.map(ue => afficheUEpourEtudiant(ue, this)).mkString("\n")

    private def afficheUEpourEtudiant(ue: UE, etudiant: Etudiant): String =
        s"${ue.nom}" + "\n" +
        ue.ects.map(t => afficheMatierepourEtudiant(t, etudiant)).mkString("\n")

    private def afficheMatierepourEtudiant(t: (Matiere, Int), etudiant: Etudiant): String =
        val matiere: Matiere = t._1
        val coef: Int = t._2
        val note: String = etudiant.notes().get(matiere).map(_.toString).getOrElse("DEF")
        s"${matiere.nom} ($coef) : $note"