package tds.td3.api

// NON FUNCTIONAL: MUTATION
trait Faculty extends Identified:

  var promotions: List[PromotionWithDelegate]
  
  // NON FUNCTIONAL: MUTATION
  def addPromotion(promotion: PromotionWithDelegate): Unit
