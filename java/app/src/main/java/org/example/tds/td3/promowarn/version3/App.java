package org.example.tds.td3.promowarn.version3;

import org.example.tds.td3.promowarn.fp.core.Pair;
import org.example.tds.td3.promowarn.common.io.*;
import org.example.tds.td3.promowarn.common.mail.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// RESUME DES CHANGEMENTS
// 1. une promotion peut ne pas avoir de délégué donc le délégué reste Student mais la méthode qui permet d'obtenir le délégué d'une promotion retourne un Optional<Student>
// 2. en conséquence on remonte des changements pour traiter ces Optional<Student> au lieu de Student dans la partie générative des courriels (App.java). On peut faire avec des if/then/else (isPresent) mais aussi des map.
// 3. un étudiant peut ne pas avoir de note, ce qui impacte le calcul de moyenne. De même que précédemment, on utilise un Optional<Double>.
// 4. idem pour la remontée jusqu'à la création des courriels.
// 5. noter aussi au passage l'utilisation des méthodes "lift" qui "ressortent" le caractère Optional (List[Option[T]] -> Option[List[T]] et (Option[T], Option[U]) -> Option[(T,U)]). Elles sont très utiles par exemple pour considérer que la création d'un courriel échoue s'il y a eu un problème avec le titre et/ou l'email du destinataire.

// POUR TROUVER LES CHANGEMENTS, utiliser la commande compare qui est fournie !
// (ou faites directement un diff - la commande unix - dans votre terminal)
// (ou encore certains IDEs permettent de faire ce type de diff entre deux fichiers, ici App.java des différentes versions)

// RAPPEL : on utilise un logger, un fichier de paramétrage de ce dernier doit être dans src/main/resources (il est dans l'archive de démarrage)

public class App {

    // un helper non disponible en Java ?
    // List[Option[T]] -> Option[List[T]]
    public static <T> Optional<List<T>> lift(List<Optional<T>> xs) {
        List<T> list = xs.stream().filter(Optional::isPresent).map(Optional::get).toList();
        return (list.isEmpty()) ? Optional.empty() : Optional.of(list);
    }

    // un helper non disponible en Java ?
    // (Option[T], Option[U]) -> Option[(T,U)]
    public static <T, U> Optional<Pair<T, U>> lift(Pair<Optional<T>, Optional<U>> p) {
        if (p.fst().isPresent() && p.snd().isPresent()) {
            return Optional.of(new Pair<>(p.fst().get(), p.snd().get()));
        } else {
            return Optional.empty();
        }
    }

    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    private static String koMessage(final Promotion p, final double m) {
        return String.format("promotion %d -- risk (%.2f)", p.id(), m);
    }

    private static String okMessage(final Promotion p, final double m) {
        return String.format("promotion %d -- no risk (%.2f)", p.id(), m);
    }

    // ici version qu'on a revue avec les Streams
    private static Optional<Double> averageStreams(final Promotion p) {
        return lift(p.students().stream().map(Student::grade).toList())
                .map(xs -> xs.stream().mapToDouble(Double::doubleValue).average().getAsDouble());
    }

    // ici version avec un for pour référence
    private static Optional<Double> average(final Promotion p) {
        double sum = 0.0;
        int nb = 0;
        for (final Student e : p.students()) {
            Optional<Double> grade = e.grade();
            if (grade.isPresent()) {
                sum += e.grade().get();
                nb++;
            }
        }
        if (nb == 0) {
            return Optional.empty();
        } else {
            return Optional.of(sum / nb);
        }
    }

    private static Optional<String> alertTitle(final Promotion p) {
        return average(p).map(avg -> (avg < 10 ? koMessage(p, avg) : okMessage(p, avg)));
    }

    private static Optional<EMailAddress> delegateEMail(final PromotionWithDelegate p) {
        return p.delegate().map(Student::email); // flatMap si email retournait un Optional aussi
    }

    // cette solution est complexe (à cause de Java): décomposez-la !
    private static Optional<Pair<EMailCategory, EMail>> createEMail(final PromotionWithDelegate p) {
        return lift(new Pair<>(delegateEMail(p), alertTitle(p)))
                .map(infos -> new Pair<>(EMailCategory.DRAFT, new EMail(infos.fst(), infos.snd())));
    }

    // cette solution est complexe (à cause de Java) : décomposez-la !
    private static Pair<EMailCategory, Optional<EMail>> createEMailAlternative(final PromotionWithDelegate p) {
        return new Pair<>(EMailCategory.DRAFT,
                lift(new Pair<>(delegateEMail(p), alertTitle(p))).map(infos -> new EMail(infos.fst(), infos.snd())));
    }

    private static void alert(final MailBox box, final Faculty f) {
        for (PromotionWithDelegate p : f.promotions()) {
            final Optional<Pair<EMailCategory, EMail>> info = createEMail(p);
            if (info.isPresent()) {
                box.prepare(info.get().fst(), info.get().snd());
            }
        }
    }

    public static final EMail DEFAULT_EMAIL = new EMail(new EMailAddress("leprof@lafac.fr"), "Ca passe pas");

    private static void alertAlternative(final MailBox box, final Faculty f) {
        for (PromotionWithDelegate p : f.promotions()) {
            final Pair<EMailCategory, Optional<EMail>> info = createEMailAlternative(p);
            box.prepare(info.fst(), info.snd().orElse(DEFAULT_EMAIL));
        }
    }

    public static void main(final String[] args) {
        DataProvider.ProviderMode mode1 = DataProvider.ProviderMode.MISSING_NOTE;
        DataProvider.ProviderMode mode2 = DataProvider.ProviderMode.NO_MISSING_NOTE;
        final DataProvider dao = new DataProvider(mode1); // TESTER avec les deux modes
        final EMailService service = new LoggerFakeEMailService(LOGGER);
        final MailBox mailbox = new MailBox(service);
        alert(mailbox, dao.faculty(1));
        LOGGER.info(mailbox);
        mailbox.sendAll();
        LOGGER.info(mailbox);
    }
}
