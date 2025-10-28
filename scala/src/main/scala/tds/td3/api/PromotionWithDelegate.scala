package tds.td3.api

// NON FUNCTIONAL: MUTATION
trait PromotionWithDelegate extends Promotion:

    def delegate: Option[Student]

    // NON FUNCTIONAL: MUTATION
    def setDelegate(delegate: Student): Unit
