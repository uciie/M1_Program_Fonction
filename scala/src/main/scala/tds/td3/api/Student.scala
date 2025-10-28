package tds.td3.api

import tds.td3.common.mail.EMailAddress

// NON FUNCTIONAL: MUTATION
trait Student extends Identified:

  val email: EMailAddress

  def grade: Option[Double]

  // NON FUNCTIONAL: MUTATION
  def setGrade(grade: Double): Unit
