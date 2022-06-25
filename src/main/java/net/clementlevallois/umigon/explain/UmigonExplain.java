/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
import net.clementlevallois.umigon.model.Decision;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.POSITIVE_TERM_THEN_NEGATION_THEN_NEGATIVE_TERM;
import net.clementlevallois.umigon.model.Decision.DecisionType;
import net.clementlevallois.umigon.model.Document;
import net.clementlevallois.umigon.model.ResultOneHeuristics;

/**
 *
 * @author LEVALLOIS
 */
public class UmigonExplain {

    private static ResourceBundle localeBundle;
    private static final String PATHLOCALE = "net.clementlevallois.umigon.explain.resources.i18n.text";

    public static void main(String[] args) {
    }

    public static ResourceBundle getLocaleBundle(String languageTag) {
        return ResourceBundle.getBundle(PATHLOCALE, Locale.forLanguageTag(languageTag));
    }

    public static String getSentiment(Document doc, String locale) {
        if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11).isEmpty()) {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive");
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative");

        } else {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral");
        }
    }

    public static String getSentiment(CategoryEnum categoryEnum, String locale) {
        switch (categoryEnum) {
            case _11:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive");
            case _12:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative");
            default:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral");
        }
    }

    public static String getExplanationsOfDecisions(Document doc, String languageTag) {
        StringBuilder sb = new StringBuilder();
        List<Decision> decisions = doc.getSentimentDecisions();
        if (decisions == null || decisions.isEmpty()) {
            return UmigonExplain.getLocaleBundle(languageTag).getString("decision.no_decision_made");
        }
        if (decisions.size() == 1) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_decision_has_been_made"));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_number_of_decisions_have_been_made"));
        }
        sb.append(":\n");
        for (Decision decision : decisions) {
            sb.append("- \"")
                    .append(decision.getHeuristicsImpacted().getTokenInvestigated())
                    .append("\" ")
                    .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.was_evaluated_as"))
                    .append(" ")
                    .append(getSentiment(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag))
                    .append(". ")
                    .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.it_was_removed_because"))
                    .append(" ")
                    .append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
            switch (decision.getDecisionMotive()) {
                case POSITIVE_TERM_THEN_NEGATION_THEN_NEGATIVE_TERM, NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM, NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM,
                        NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM:
                    sb.append(". ")
                            .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                            .append(" ").append(decision.getTermInvolvedInDecision()).append("\"");
                case MODERATOR_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM,NEGATIVE_TERM_THEN_MODERATOR,
        POSITIVE_TERM_THEN_MODERATOR,
        TWO_POSITIVE_TERMS_THEN_MODERATOR,
        TWO_NEGATIVE_TERMS_THEN_MODERATOR:
                    sb.append(". ")
                            .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                            .append(" \"").append(decision.getTermInvolvedInDecision()).append("\"");
            }

            sb.append(".").append("\n");
        }
        return sb.toString();
    }

    public static String getExplanation(Document doc, String languageTag) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForPositive = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11);
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForNegative = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12);
        Collection<ResultOneHeuristics> resultsHeuristics = null;
        boolean sentimentDetected = false;
        if (!allHeuristicsResultsForPositive.isEmpty()) {
            sentimentDetected = true;
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.ispositive"));
            resultsHeuristics = allHeuristicsResultsForPositive.values();
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            sentimentDetected = true;
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isnegative"));
            resultsHeuristics = allHeuristicsResultsForNegative.values();
        }
        if (sentimentDetected) {
            sb.append(" ");
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.because"));
            sb.append(" ");
            sb.append(ExaminingAllResultsHeuristics.goThroughAllResultsHeuristics(resultsHeuristics, languageTag));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isneutral"));
        }
        return sb.toString();
    }
}
