package tds.td3.common.fp

/**
  * A collection of helper methods / functions.
  * Some may already exist (in Scala or in Cats).
  */
object Helpers:

  def lift[A](xs: List[Option[A]]): Option[List[A]] =
    xs.flatMap(x => x.toList) match { case x::xs => Some(x::xs); case Nil => None }

  def lift[A,B](t: (Option[A], Option[B])): Option[(A, B)] =
    if (t._1.isEmpty || t._2.isEmpty) then None else Some(t._1.get, t._2.get)

  def lift[A,B,C](f: (A, B) => C): (Option[A], Option[B]) => Option[C] =
    (a, b) => if (a.isEmpty || b.isEmpty) then None else Some(f(a.get, b.get))  

  def lift1[A,B](t: (Option[A], B)): Option[(A, B)] =
    if (t._1.isEmpty) then None else Some(t._1.get, t._2)

  def lift2[A,B](t: (A, Option[B])): Option[(A, B)] =
    if (t._2.isEmpty) then None else Some(t._1, t._2.get)

  def lift1[A,B,C](f: (A, B) => C): (Option[A], B) => Option[C] =
    (a, b) => if (a.isEmpty) then None else Some(f(a.get, b))

  def lift2[A,B,C](f: (A, B) => C): (A, Option[B]) => Option[C] =
    (a, b) => if (b.isEmpty) then None else Some(f(a, b.get))
