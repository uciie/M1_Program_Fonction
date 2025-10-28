package tds.td3.impl

import tds.td3.api.{Faculty, PromotionWithDelegate}

// NON FUNCTIONAL: MUTATION
case class FacultyImpl(id: Int, var promotions: List[PromotionWithDelegate] = List()) extends Faculty:
    
    // NON FUNCTIONAL: MUTATION
    override def addPromotion(promotion: PromotionWithDelegate): Unit =
        promotions = promotions.appended(promotion)

    override def toString(): String =
        s"faculty $id" + "\n" + promotions.map(_.toString).mkString("\n")
