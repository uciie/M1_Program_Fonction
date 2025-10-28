package tds.td3.impl

import tds.td3.api.{Promotion, Student}

// NON FUNCTIONAL: MUTATION
class PromotionImpl(val id: Int, var students: Set[Student]) extends Promotion:

    // NON FUNCTIONAL: MUTATION
    override def addStudent(student: Student): Unit =
        students = students.union(Set(student))

    override def toString(): String =
        s"promotion $id" + " -- students: " + students.mkString(", ")
