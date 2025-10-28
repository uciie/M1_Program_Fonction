package tds.td3

import tds.td3.common.io.LoggerEMailService
import tds.td3.common.mail.{EMailCategory, EMail, MailBox}
import tds.td3.api.{Faculty, PromotionWithDelegate}

object App:

    def message(info: String)(promotion: PromotionWithDelegate, average: Double): String =
        s"promotion ${promotion.id} -- $info ($average)"

    val koMessage = message("risk")
    val okMessage = message("no risk")

    def average(promotion: PromotionWithDelegate): Option[Double] =
        return Option(promotion.students.toList.flatMap(_.grade))
                    .filter(_.nonEmpty)
                    .map(notes => notes.sum / notes.size)

    def alertTitle(promotion: PromotionWithDelegate): Option[String] =
        average(promotion).map(a => if (a < 10) 
                                        then koMessage(promotion, a)
                                        else okMessage(promotion, a))

    def createEMail(promotion: PromotionWithDelegate): Option[(EMailCategory, EMail)] =
        for {
            delegateEMail <- promotion.delegate.map(_.email)
            title <- alertTitle(promotion)
        } yield (EMailCategory.DRAFT, EMail(delegateEMail, title))

    def alert(mailbox: MailBox, faculty: Faculty): Unit =
        faculty.promotions.flatMap(createEMail).foreach(mailbox.prepare)

    @main
    def main(): Unit =
        val MISSING_GRADES = true // true or false
        val dao = if MISSING_GRADES
            then DAO.daoWithoutAllGrades
            else DAO.daoWithAllGrades
        val service = LoggerEMailService()
        val mailbox = MailBox(service)
        println(dao)
        println(mailbox)
        dao.faculty(1).foreach(alert(mailbox, _))
        println(mailbox)
        mailbox.sendAll
        println(mailbox)
