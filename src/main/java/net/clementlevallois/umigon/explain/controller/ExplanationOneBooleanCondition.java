/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Locale;
import net.clementlevallois.umigon.model.BooleanCondition;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneBooleanCondition {

    public static String getExplanationOneBooleanConditonPlainText(BooleanCondition booleanCondition, String languageTag) {
        StringBuilder sb = new StringBuilder();
        sb.append(getConditionalExpressionName(booleanCondition.getTokenInvestigatedGetsMatched(), booleanCondition.getBooleanConditionEnum(), booleanCondition.getFlipped(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
        if (booleanCondition.getKeywordMatched() != null && !booleanCondition.getKeywordMatched().isEmpty()) {
            sb.append(" (\"");
            sb.append(booleanCondition.getKeywordMatched());
            sb.append("\")");
        } else if (!booleanCondition.getKeywords().isEmpty()) {
            sb.append(" (\"");
            sb.append(String.join(", ", booleanCondition.getKeywords()));
            sb.append("\")");
        }
        sb.append(", ");
        return sb.toString();
    }

    public static String getExplanationOneBooleanConditonHtml(BooleanCondition booleanCondition, String languageTag) {
        StringBuilder sb = new StringBuilder();
        sb.append(getConditionalExpressionName(booleanCondition.getTokenInvestigatedGetsMatched(), booleanCondition.getBooleanConditionEnum(), booleanCondition.getFlipped(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
        if (booleanCondition.getKeywordMatched() != null && !booleanCondition.getKeywordMatched().isEmpty()) {
            sb.append(" (\"");
            sb.append(booleanCondition.getKeywordMatched());
            sb.append("\")");
        } else if (!booleanCondition.getKeywords().isEmpty()) {
            sb.append(" (\"");
            sb.append(String.join(", ", booleanCondition.getKeywords()));
            sb.append("\")");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationOneBooleanConditonJsonObject(BooleanCondition booleanCondition, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("conditional expression", getConditionalExpressionName(booleanCondition.getTokenInvestigatedGetsMatched(), booleanCondition.getBooleanConditionEnum(), booleanCondition.getFlipped(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
        if (booleanCondition.getFlipped() & !booleanCondition.getTokenInvestigatedGetsMatched()) {
            job.add("keyword(s) not matched", String.join(", ", booleanCondition.getKeywords()));
        } else {
            job.add("keyword matched", booleanCondition.getKeywordMatched());
        }
        return job;
    }

    private static String getConditionalExpressionName(Boolean matched, BooleanCondition.BooleanConditionEnum condition, boolean flipped, String languageTag) {
        if (flipped & !matched) {
            return UmigonExplain.getLocaleBundle(languageTag).getString("condition.name.not." + condition.name());
        } else {
            return UmigonExplain.getLocaleBundle(languageTag).getString("condition.name." + condition.name());
        }
    }

//    private static String getKeywordMatched(BooleanCondition.BooleanConditionEnum condition, String keyword) {
//        switch (condition) {
//            case isImmediatelyPrecededByANegation,                
//        isImmediatelyFollowedByTimeIndication,
//        isImmediatelyFollowedByANegation,
//        isImmediatelyPrecededBySpecificTerm,
//        isImmediatelyFollowedBySpecificTerm,
//        isImmediatelyFollowedByAnOpinion,
//        isPrecededBySubjectiveTerm,
//        isFollowedByVerbPastTense,
//        isFollowedByAPositiveOpinion,
//        isImmediatelyPrecededByPositive,
//        isImmediatelyPrecededByNegative,
//        isImmediatelyFollowedByAPositiveOpinion,
//        isImmediatelyFollowedByANegativeOpinion,
//        isPrecededByOpinion,
//        isPrecededByPositive,
//        isPrecededBySpecificTerm,
//        isFollowedBySpecificTerm,
//        isInATextWithOneOfTheseSpecificTerms,
//        isPrecededByStrongWord:
//                return keyword;
//
//            case isFirstLetterCapitalized,
//        isNegationInCaps,
//        isHashtagStart,
//        isHashtag,
//        isInHashtag,
//        isHashtagPositiveSentiment,
//        isHashtagNegativeSentiment,
//        isAllCaps,
//        isQuestionMarkAtEndOfText,
//        isFirstTermOfText:
//                return "";
//
//            default:
//                return "";
//
//        }
//    }
}
