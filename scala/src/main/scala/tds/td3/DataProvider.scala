package tds.td3

import tds.td3.api.{Faculty, PromotionWithDelegate, Student}
import tds.td3.impl.{FacultyImpl, PromotionWithDelegateImpl, StudentImpl}

// NON FUNCTIONAL: MUTATION
class DataProvider():
    private var _students: Map[Int, Student] = Map()
    private var _faculties: Map[Int, Faculty] = Map()

    def student(id: Int): Option[Student] = _students.get(id)
    def faculty(id: Int): Option[Faculty] = _faculties.get(id)

    // NON FUNCTIONAL: MUTATION
    def withFaculty(id: Int): DataProvider =
        _faculties = _faculties.updated(id, FacultyImpl(id))
        this

    // NON FUNCTIONAL: MUTATION
    def withStudent(id: Int, email: String): DataProvider =
        _students = _students.updated(id, StudentImpl(id, email))
        this

    // NON FUNCTIONAL: MUTATION
    def withPromotion(idFaculty: Int, idPromotion: Int, idEtudiants: Set[Int], idDelegue: Option[Int] = None): DataProvider =
        var faculty: Option[Faculty] = _faculties.get(idFaculty)
        val students: Set[Student] = idEtudiants.flatMap(_students.get)
        val delegate: Option[Student] = idDelegue.flatMap(_students.get)
        if (faculty.isDefined && students.nonEmpty)
            val promotion: PromotionWithDelegate = PromotionWithDelegateImpl(idPromotion, students)
            if (delegate.isDefined) then promotion.setDelegate(delegate.get)
            faculty.get.addPromotion(promotion)
            _faculties = _faculties.updated(idFaculty, faculty.get)
        this

    // NON FUNCTIONAL: MUTATION
    def withGrading(idEtudiant: Int, grade: Double): DataProvider =
        _students.get(idEtudiant).foreach(e => e.setGrade(grade))
        this

    override def toString(): String =
        val facultiesRepr = _faculties.values.map(f => f.toString).mkString("\n")
        val studentsRepr = _students.values.map(s => s.toString).mkString("\n")
        s"\n## Faculties\n\n$facultiesRepr\n\n## Students\n$studentsRepr"

object DAO:

    def daoWithAllGrades: DataProvider = daoWithoutAllGrades.withGrading(7, 5)

    def daoWithoutAllGrades: DataProvider =
        DataProvider()
        // création des facultés
        .withFaculty(1)
        // inscription des étudiants
        .withStudent(1, "email1@uni-foo.edu")
        .withStudent(2, "email2@uni-foo.edu")
        .withStudent(3, "email3@uni-foo.edu")
        .withStudent(4, "email4@uni-foo.edu")
        .withStudent(5, "email5@uni-foo.edu")
        .withStudent(6, "email6@uni-foo.edu")
        .withStudent(7, "email7@uni-foo.edu")
        // creation des promotions
        .withPromotion(1, 1, Set(1, 2), Some(1))
        .withPromotion(1, 2, Set(3, 4), Some(1)) // délégué pas de la promo : bug volontaire
        .withPromotion(1, 3, Set(5, 6), Some(5))
        .withPromotion(1, 4, Set(7), Some(7))
        // notation des étudiants
        .withGrading(1, 12)
        .withGrading(2, 14)
        .withGrading(3, 4)
        .withGrading(4, 6)
        .withGrading(5, 8)
        .withGrading(6, 10)
        //.withGrading(7, 5) // ligne commentée, absence de note = bug volontaire
