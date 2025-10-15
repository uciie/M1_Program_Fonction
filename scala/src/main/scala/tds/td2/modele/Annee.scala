package tds.td2.modele

// possible fuite de donnée + non fonctionnel
// modif modèle pour cohérence inscriptions
final case class Annee(ues: Set[UE]):

    var etudiants: Set[Etudiant] = Set()

    def inscrire(etudiant: Etudiant): Unit =
        etudiant.inscrire(this)
        etudiants = etudiants.union(Set(etudiant))
