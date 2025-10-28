package tds.td3.common.io

import tds.td3.common.mail.EMail

/**
  * A service that can send e-mails.
  * NON FUNCTIONAL: IO.
  */
trait EMailService:

  /**
    * Sends an e-mail.
    * NON FUNCTIONAL: IO.
    *
    * @param email the e-mail to be sent.
    */
  def send(email: EMail): Unit

