package tds.td3.api

// NON FUNCTIONAL: MUTATION
trait Promotion extends Identified:

  var students: Set[Student]
  
  // NON FUNCTIONAL: MUTATION
  def addStudent(student: Student): Unit
