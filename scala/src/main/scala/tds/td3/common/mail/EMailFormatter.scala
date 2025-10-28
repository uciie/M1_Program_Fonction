package tds.td3.common.mail

/**
  * Any class that formats an e-mail to a String.
  */
trait EMailFormatter:

  /**
    * Formats the given e-mail into a string.
    *
    * @param email the e-mail that should be formatted.
    * @return the formatted e-mail as a string.
    */
  def format(email: EMail): String
