package tds.td3.common.mail

import tds.td3.common.io.EMailService

/**
  * A simple mail box organized in two categories : DRAFT and SENT.
  *
  * @param _service the EMailService used to send e-mails.
  * @param _formatter the EMailFormatter used to format e-mails before sending them.
  */
class MailBox(_service: EMailService, _formatter: EMailFormatter):

    def this(_service: EMailService) = this(_service, MailBox.defaultEMailFormatter)

    private var _boxes: Map[EMailCategory, List[EMail]] = 
        EMailCategory.values.map(cat => (cat -> List())).toMap

    // NON FUNCTIONAL: MUTATION
    def prepare(category: EMailCategory, email: EMail): Unit =
        _boxes = _boxes ++ Map(category -> _boxes.getOrElse(category, List()).appended(email))

    // NON FUNCTIONAL: MUTATION
    def sendAll: Unit = {
        _boxes.get(EMailCategory.DRAFT)
            .map(emails => {
                emails.foreach(email => _service.send(email))
                _boxes = _boxes ++ Map(EMailCategory.DRAFT -> List()) ++ Map(EMailCategory.SENT -> emails)
            })
    }

    override def toString(): String =
        "## MailBox\n" +
        _boxes.map({ case (category, emails) => 
                        s"\t$category :\n" + emails.map(email => s"\t\t${email}\n").mkString
        }).mkString("\n")

object MailBox:
    val defaultEMailFormatter: EMailFormatter = email => s"""
        | mail to: ${email.toAddress}
        | subject: ${email.title}
        """
