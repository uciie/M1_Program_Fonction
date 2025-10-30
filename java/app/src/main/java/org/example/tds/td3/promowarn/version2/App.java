package org.example.tds.td3.promowarn.version2;

import org.example.tds.td3.promowarn.fp.core.Pair;
import org.example.tds.td3.promowarn.version1.Faculty;
import org.example.tds.td3.promowarn.version1.Promotion;
import org.example.tds.td3.promowarn.version1.PromotionWithDelegate;
import org.example.tds.td3.promowarn.version1.Student;
import org.example.tds.td3.promowarn.common.io.*;
import org.example.tds.td3.promowarn.common.mail.*;

// inutile mais IMPORTANT : on utilise bien le provider version 2
// import org.example.td3.promowarn.version2.DataProvider;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    private static String koMessage(final Promotion p, final double m) {
        return String.format("promotion %d -- risk (%.2f)", p.id(), m);
    }

    private static String okMessage(final Promotion p, final double m) {
        return String.format("promotion %d -- no risk (%.2f)", p.id(), m);
    }

    private static Double average(final Promotion p) {
        double sum = 0.0;
        int nb = 0;
        for (final Student e : p.students()) {
            sum += e.grade();
            nb++;
        }
        return sum / nb;
    }

    private static String alertTitle(final Promotion p) {
        final double avg = average(p);
        return avg < 10 ? koMessage(p, avg) : okMessage(p, avg);
    }
    
    private static EMailAddress delegateEMail(final PromotionWithDelegate p) {
        // MODIFICATION
        Student delegate = p.delegate();
        if (delegate != null)
            return delegate.email();
        else
            return null;
    }

    private static Pair<EMailCategory, EMail> createEMail(final PromotionWithDelegate p) {
        final EMailAddress email = delegateEMail(p);
        final String title = alertTitle(p);
        return new Pair<>(EMailCategory.DRAFT, new EMail(email, title));
    }

    private static void alert(final MailBox box, final Faculty f) {
        for (PromotionWithDelegate p : f.promotions()) {
            final Pair<EMailCategory, EMail> info = createEMail(p);
            box.prepare(info.fst(), info.snd());
        }
    }

    public static void main(final String[] args) {
        DataProvider.ProviderMode mode1 = DataProvider.ProviderMode.MISSING_NOTE;
        DataProvider.ProviderMode mode2 = DataProvider.ProviderMode.NO_MISSING_NOTE;
        final DataProvider dao = new DataProvider(mode1); // TESTER AVEC mode1 PUIS mode2
        final EMailService service = new LoggerFakeEMailService(LOGGER);
        final MailBox mailbox = new MailBox(service);
        alert(mailbox, dao.faculty(1));
        LOGGER.info(mailbox);
        mailbox.sendAll();
        LOGGER.info(mailbox);
    }
}
