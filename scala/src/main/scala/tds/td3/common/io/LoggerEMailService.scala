package tds.td3.common.io

import tds.td3.common.mail.EMail

/**
  * A simple (fake) implementation of an EMailService.
  * Here no real e-mail sending, no logger, just println.
  * NON FUNCTIONAL: IO.
  */
class LoggerEMailService extends EMailService:

  override def send(email: EMail): Unit = println("sending " + email)
