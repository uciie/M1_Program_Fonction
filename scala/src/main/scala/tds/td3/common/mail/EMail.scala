package tds.td3.common.mail

/**
  * An e-mail made up of a receiver and a title.
  *
  * @constructor creates an e-mail with a receiver and a title.
  * @param toAddress the receiver of this e-mail (public).
  * @param title the title of this e-mail (public).
  */
final case class EMail(toAddress: EMailAddress, title: String):

    override def toString = s"email $title for $toAddress"
