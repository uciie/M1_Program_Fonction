package tds.td3.impl

import tds.td3.api.{PromotionWithDelegate, Student}

// NON FUNCTIONAL: MUTATION
class PromotionWithDelegateImpl(id: Int, students: Set[Student]) extends PromotionImpl(id=id, students=students) with PromotionWithDelegate:

  private var _delegate: Option[Student] = None

  override def delegate: Option[Student] = _delegate

  // NON FUNCTIONAL: MUTATION
  override def setDelegate(student: Student): Unit =
    if (students.contains(student)) then _delegate = Option.apply(student)

  override def toString(): String =
    super.toString() + s" -- delegate: " + delegate.map(_.toString).getOrElse("none")
