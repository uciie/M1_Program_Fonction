package tds.td3.common.mail

/**
  * An e-mail address.
  *
  * @constructor creates an e-mail address from a string.
  * @param address the e-mail address as a string (public)
  */
final case class EMailAddress(address: String):

    override def toString = address
