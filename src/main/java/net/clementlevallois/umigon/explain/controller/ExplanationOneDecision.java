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
import net.clementlevallois.umigon.model.Category.CategoryEnum;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneDecision {

    public static String getExplanationOneDecisionPlainText(Decision decision, String languageTag) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"")
                .append(decision.getHeuristicsImpacted().getTextFragmentInvestigated().getOriginalForm())
                .append("\" ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.was_evaluated_as"))
                .append(" ")
                .append(getSentimentPlainText(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag))
                .append(". ")
                .append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.it_was_removed_because"))
                .append(" ");
        if (decision.getDecisionMotive().equals(Decision.DecisionMotive.WINNER_TAKES_ALL)) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString() + "." + decision.getTextFragmentInvolvedInDecision().getTypeOfTextFragmentEnum()));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
        }
        if (decision.getTextFragmentInvolvedInDecision() != null) {
            sb.append(": ")
                    .append("\"")
                    .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                    .append("\".");
            return sb.toString();

        } else if (!sb.toString().endsWith(".")) {
            sb.append(".");
        }

        switch (decision.getDecisionMotive()) {
            case POSITIVE_TERM_THEN_NEGATION_THEN_NEGATIVE_TERM:
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM:
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM:
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM:
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case MODERATOR_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM:
                if (!sb.toString().endsWith(". ")) {
                    sb.append(". ");
                }
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" \"").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case NEGATIVE_TERM_THEN_MODERATOR:
                if (!sb.toString().endsWith(". ")) {
                    sb.append(". ");
                }
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" \"").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case POSITIVE_TERM_THEN_MODERATOR:
                if (!sb.toString().endsWith(". ")) {
                    sb.append(". ");
                }
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" \"").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case TWO_POSITIVE_TERMS_THEN_MODERATOR:
                if (!sb.toString().endsWith(". ")) {
                    sb.append(". ");
                }
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" \"").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case TWO_NEGATIVE_TERMS_THEN_MODERATOR:
                if (!sb.toString().endsWith(". ")) {
                    sb.append(". ");
                }
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" \"").append(decision.getTextFragmentInvolvedInDecision().getOriginalForm()).append("\"");
                break;
            case WINNER_TAKES_ALL:
            //nothing here because we treated this case above the switch statement
        }
        return sb.toString();
    }

    public static String getExplanationOneDecisionHtml(Decision decision, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        if (decision.getDecisionMotive() == null){
            System.out.println("decision without motive:");
            System.out.println(decision.getDecisionType().name());
            System.out.println(decision.getTextFragmentInvolvedInDecision().getOriginalForm());
            return sb.toString();
        }
        sb
                .append("\"<span style=\"color:")
                .append(htmlSettings.getTermColorBasedOnSentiment(decision.getHeuristicsImpacted().getCategoryEnum()))
                .append("\">")
                .append(decision.getHeuristicsImpacted().getTextFragmentInvestigated().getOriginalForm())
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
                .append(" ");
        if (decision.getDecisionMotive().equals(Decision.DecisionMotive.WINNER_TAKES_ALL)) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString() + "." + decision.getTextFragmentInvolvedInDecision().getTypeOfTextFragmentEnum()));
            sb.append(": ")
                    .append("\"")
                    .append("<span style=\"color:");
            if (!decision.getListOfHeuristicsImpacted().isEmpty() && decision.getListOfHeuristicsImpacted().get(0).getCategoryEnum().equals(CategoryEnum._11)) {
                sb.append(htmlSettings.getNegativeTermColor());
            } else if (!decision.getListOfHeuristicsImpacted().isEmpty() && decision.getListOfHeuristicsImpacted().get(0).getCategoryEnum().equals(CategoryEnum._12)) {
                sb.append(htmlSettings.getPositiveTermColor());
            } else {
                sb.append(htmlSettings.getModeratorTermColor());
            }
            sb.append("\">")
                    .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                    .append("</span>")
                    .append("\"");
            return sb.toString();
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
        }
        if (!sb.toString().endsWith(".")) {
            sb.append(".");
        }
        switch (decision.getDecisionMotive()) {
            case POSITIVE_TERM_THEN_NEGATION_THEN_NEGATIVE_TERM:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ")
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getNegationTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case NEGATIVE_TERM_THEN_NEGATION_THEN_POSITIVE_TERM:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ")
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getNegationTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case NEGATION_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ")
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getNegationTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case NEGATION_THEN_POSITIVE_TERM_THEN_NEGATIVE_TERM:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_negative_term_is"))
                        .append(" ")
                        .append("\"<span style=\"color:")
                        .append(htmlSettings.getNegationTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case MODERATOR_THEN_NEGATIVE_TERM_THEN_POSITIVE_TERM:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" ")
                        .append("\"")
                        .append("<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case NEGATIVE_TERM_THEN_MODERATOR:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" ")
                        .append("\"")
                        .append("<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case POSITIVE_TERM_THEN_MODERATOR:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" ")
                        .append("\"")
                        .append("<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case TWO_POSITIVE_TERMS_THEN_MODERATOR:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" ")
                        .append("\"")
                        .append("<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case TWO_NEGATIVE_TERMS_THEN_MODERATOR:
                sb.append(" ");
                sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.the_moderator_is"))
                        .append(" ")
                        .append("\"")
                        .append("<span style=\"color:")
                        .append(htmlSettings.getModeratorTermColor())
                        .append("\">")
                        .append(decision.getTextFragmentInvolvedInDecision().getOriginalForm())
                        .append("</span>")
                        .append("\"");
                break;
            case WINNER_TAKES_ALL:
            //nothing here because we treated this case above the switch statement
        }

        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationOneDecisionJsonObject(Decision decision, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("token removed", decision.getHeuristicsImpacted().getTextFragmentInvestigated().getOriginalForm());
        job.add("token - sentiment associated to it", getSentimentPlainText(decision.getHeuristicsImpacted().getCategoryEnum(), languageTag));
        job.add("decision type", UmigonExplain.getLocaleBundle(languageTag).getString("decision.type." + decision.getDecisionType().toString()));
        if (decision.getDecisionMotive().equals(Decision.DecisionMotive.WINNER_TAKES_ALL)) {
            job.add("decision motive", UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString() + "." + decision.getTextFragmentInvolvedInDecision().getTypeOfTextFragmentEnum()));
        } else {
            job.add("decision motive", UmigonExplain.getLocaleBundle(languageTag).getString("decision.motive." + decision.getDecisionMotive().toString()));
        }
        if (decision.getTextFragmentInvolvedInDecision() != null) {
            job.add("term involved in the decision", decision.getTextFragmentInvolvedInDecision().getOriginalForm());
        } else {
            job.add("term involved in the decision", "");
        }
        return job;
    }
}
