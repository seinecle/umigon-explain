/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import net.clementlevallois.umigon.explain.parameters.HtmlSettings;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneHeuristics {

    public static String getOneHeuristicsResultsPlainText(ResultOneHeuristics resultOneHeuristics, String languageTag) {
        StringBuilder sb = new StringBuilder();
        List<BooleanCondition> booleanConditions = resultOneHeuristics.getBooleanConditions();
        // because we don't care to know about conditions that needed to NOT be fulfilled
        Collection<BooleanCondition> nonFlippedBooleanConditions = booleanConditions.stream().filter(x -> !x.getFlipped() & !x.getBooleanConditionEnum().equals(BooleanCondition.BooleanConditionEnum.none)).collect(Collectors.toList());

        if (resultOneHeuristics.getCategoryEnum().equals(Category.CategoryEnum._10)) {
            return sb.toString();
        }
        sb.append(getTokenWasMatched(resultOneHeuristics.getTypeOfToken(), languageTag));
        sb.append(": \"");

        sb.append(resultOneHeuristics.getTokenInvestigated());
        if (nonFlippedBooleanConditions.isEmpty()) {
            return sb.append("\". ").toString();
        } else {
            sb.append("\", ");
            sb.append(getAndANumberOfConditionsWereMatched(nonFlippedBooleanConditions.size(), languageTag));
            sb.append(":\n");
        }

        for (BooleanCondition booleanCondition : nonFlippedBooleanConditions) {
            sb.append(ExplanationOneBooleanCondition.getExplanationOneBooleanConditonPlainText(booleanCondition, languageTag));
        }
        if (sb.toString().endsWith(", ")) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        return sb.toString();
    }

    public static String getOneHeuristicsResultsHtml(ResultOneHeuristics resultOneHeuristics, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        List<BooleanCondition> booleanConditions = resultOneHeuristics.getBooleanConditions();
        // because we don't care to know about conditions that needed to NOT be fulfilled
        Collection<BooleanCondition> nonFlippedBooleanConditions = booleanConditions.stream().filter(x -> !x.getFlipped() & !x.getBooleanConditionEnum().equals(BooleanCondition.BooleanConditionEnum.none)).collect(Collectors.toList());

        if (resultOneHeuristics.getCategoryEnum().equals(Category.CategoryEnum._10)) {
            return sb.toString();
        }
        sb.append(getTokenWasMatched(resultOneHeuristics.getTypeOfToken(), languageTag));
        sb.append(":");
        sb.append("<br/>");
        sb.append("\"");
        sb.append("<span style=\"color:")
                .append(htmlSettings.getTermColorBasedOnSentiment(resultOneHeuristics.getCategoryEnum()))
                .append("\">");
        sb.append(resultOneHeuristics.getTokenInvestigated());
        sb.append("</span>");
        sb.append("\"");
        if (nonFlippedBooleanConditions.isEmpty()) {
            return sb.append(". ").toString();
        } else {
            sb.append(", ");
            sb.append(getAndANumberOfConditionsWereMatched(nonFlippedBooleanConditions.size(), languageTag));
            sb.append(":");
            sb.append("<br/>");
        }

        sb.append("<ul>");
        for (BooleanCondition booleanCondition : nonFlippedBooleanConditions) {
            sb.append("<li>");
            sb.append(ExplanationOneBooleanCondition.getExplanationOneBooleanConditonPlainText(booleanCondition, languageTag));
            sb.append("</li>");
        }
        sb.append("</ul>");
        sb.append("<br/>");
        return sb.toString();
    }

    public static JsonObjectBuilder getOneHeuristicsResultsJsonObject(ResultOneHeuristics resultOneHeuristics, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();

        List<BooleanCondition> booleanConditions = resultOneHeuristics.getBooleanConditions();
        // because we don't care to know about conditions that needed to NOT be fulfilled
        Collection<BooleanCondition> nonFlippedBooleanConditions = booleanConditions.stream().filter(x -> !x.getFlipped() & !x.getBooleanConditionEnum().equals(BooleanCondition.BooleanConditionEnum.none)).collect(Collectors.toList());

        if (resultOneHeuristics.getCategoryEnum().equals(Category.CategoryEnum._10)) {
            return job;
        }
        job.add("type of token matched", resultOneHeuristics.getTypeOfToken().toString());

        job.add("token matched", resultOneHeuristics.getTokenInvestigated());
        if (nonFlippedBooleanConditions.isEmpty()) {
            return job;
        }
        int i = 0;
        for (BooleanCondition booleanCondition : nonFlippedBooleanConditions) {
            job.add(String.valueOf(i++), ExplanationOneBooleanCondition.getExplanationOneBooleanConditonJsonObject(booleanCondition, languageTag));
        }
        return job;
    }

    public static String getTermWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.term_was_matched");
    }

    public static String getAndANumberOfConditionsWereMatched(int numberOfConditions, String languageTag) {
        switch (numberOfConditions) {
            case 1:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.and_one_condition_was_met");
            case 2:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.and_two_conditions_were_met");
            case 3:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.and_three_conditions_were_met");

            default:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.and_these_conditions_were_met");
        }

    }

    public static String getTokenWasMatched(TypeOfTokenEnum typeOfTokenEnum, String languageTag) {
        switch (typeOfTokenEnum) {
            case NGRAM:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.term_was_matched");
            case EMOJI:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.emoji_was_matched");
            case ONOMATOPAE:
                return UmigonExplain.getLocaleBundle(languageTag).getString("onomatopae.term_was_matched");
            case HASHTAG:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.hashtag_was_matched");
            case EMOTICON_IN_ASCII:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.emoticon_in_ascii_was_matched");
            default:
                return UmigonExplain.getLocaleBundle(languageTag).getString("statement.term_was_matched");
        }

    }

    public static String getEmojiWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.emoji_was_matched");
    }

    public static String getOnomatopaeWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.onomatopae_was_matched");
    }

    public static String getHashtagWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.hashtag_was_matched");
    }
}
