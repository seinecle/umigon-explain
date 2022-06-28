/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import net.clementlevallois.umigon.model.Decision;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATIVE_TERM_THEN_MODERATOR;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.POSITIVE_TERM_THEN_MODERATOR;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.TWO_NEGATIVE_TERMS_THEN_MODERATOR;
import static net.clementlevallois.umigon.model.Decision.DecisionMotive.TWO_POSITIVE_TERMS_THEN_MODERATOR;
import static net.clementlevallois.umigon.explain.controller.UmigonExplain.getSentimentPlainText;
import net.clementlevallois.umigon.explain.parameters.HtmlSettings;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneDecision {

    public static String getExplanationOneDecisionPlainText(Decision decision, String languageTag) {
        StringBuilder sb = new StringBuilder();
        sb.append("- \"")
                .append(decision.getHeuristicsImpacted().getTokenInvestigated())
                .append("\" ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.was_evaluated_as"))
                .append(" ")
                .append(getSentimentPlainText(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag))
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

        return sb.toString();
    }

    public static String getExplanationOneDecisionHtml(Decision decision, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\"<span style=\"color:")
                .append(htmlSettings.getTermColorBasedOnSentiment(decision.getHeuristicsImpacted().getCategoryEnum()))
                .append("\">")
                .append(decision.getHeuristicsImpacted().getTokenInvestigated())
                .append("</span>\" ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.was_evaluated_as"))
                .append(" ")
                .append("\"<span style=\"color:")
                .append(htmlSettings.getTermColorBasedOnSentiment(decision.getHeuristicsImpacted().getCategoryEnum()))
                .append("\">")
                .append(getSentimentPlainText(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag))
                .append("</span>")
                .append("\". ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.it_was_removed_because"))
                .append(" ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
        switch (decision.getDecisionMotive()) {
            case POSITIVE_TERM_THEN_NEGATION_THEN_NEGATIVE_TERM, NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM, NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM,
                        NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM:
                sb.append(". ")
                        .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ")
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getNegationTermColor())
                        .append("\">")
                        .append(decision.getTermInvolvedInDecision())
                        .append("</span>")
                        .append("\"");
            case MODERATOR_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM,NEGATIVE_TERM_THEN_MODERATOR,
        POSITIVE_TERM_THEN_MODERATOR,
        TWO_POSITIVE_TERMS_THEN_MODERATOR,
        TWO_NEGATIVE_TERMS_THEN_MODERATOR:
                sb.append(". ")
                        .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTermInvolvedInDecision())
                        .append("</span>");
        }

        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationOneDecisionJsonObject(Decision decision, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("token removed", decision.getHeuristicsImpacted().getTokenInvestigated());
        job.add("token - sentiment associated to it", getSentimentPlainText(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag));
        job.add("decision motive", UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
        job.add("decision type", UmigonExplain.getLocaleBundle(languageTag).getString("decision.type." + decision.getDecisionType().toString()));
        job.add("term involved in the decision", decision.getTermInvolvedInDecision());
        return job;
    }

}
