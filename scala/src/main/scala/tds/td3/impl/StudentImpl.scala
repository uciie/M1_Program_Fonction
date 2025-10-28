package tds.td3.impl

import tds.td3.common.mail.EMailAddress
import tds.td3.api.Student

// NON FUNCTIONAL: MUTATION
class StudentImpl(override val id: Int, _email: String) extends Student:

    private var _grade: Option[Double] = None

    override val email = EMailAddress(_email)

    override def grade: Option[Double] = _grade

    // NON FUNCTIONAL: MUTATION
    override def setGrade(g: Double): Unit =
        _grade = Option.apply(g)

    override def equals(x: Any): Boolean = 
        if (x == null || getClass!= x.getClass) false
        else id == x.asInstanceOf[StudentImpl].id

    override def hashCode(): Int = id

    override def toString(): String =
        val gradeRepr = grade.map(_.toString).getOrElse("no grade")
        s"$id (email: $email): $gradeRepr"
