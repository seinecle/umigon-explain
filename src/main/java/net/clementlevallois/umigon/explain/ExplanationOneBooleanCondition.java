/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Locale;
import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isAllCaps;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFirstTermOfText;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedByAPositiveOpinion;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedBySpecificTerm;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFollowedByVerbPastTense;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtag;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagNegativeSentiment;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagPositiveSentiment;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagStart;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegation;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByANegativeOpinion;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByAPositiveOpinion;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByAnOpinion;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedBySpecificTerm;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyFollowedByTimeIndication;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByNegative;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededByPositive;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isImmediatelyPrecededBySpecificTerm;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInATextWithOneOfTheseSpecificTerms;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isNegationInCaps;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByOpinion;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByPositive;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySpecificTerm;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededByStrongWord;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isPrecededBySubjectiveTerm;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isQuestionMarkAtEndOfText;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneBooleanCondition {

    public static String getExplanationOneBooleanConditonPlainText(BooleanCondition booleanCondition, String languageTag) {
        StringBuilder sb = new StringBuilder();
        sb.append("- ");
        sb.append(getConditionalExpressionName(booleanCondition.getBooleanConditionEnum(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
        if (booleanCondition.getKeywordMatched() != null && !booleanCondition.getKeywordMatched().isEmpty()) {
            sb.append(" ");
            sb.append(getKeywordMatched(booleanCondition.getBooleanConditionEnum(), booleanCondition.getKeywordMatched()));
        }
        sb.append("\n");
        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationOneBooleanConditonJsonObject(BooleanCondition booleanCondition, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("conditional expression", getConditionalExpressionName(booleanCondition.getBooleanConditionEnum(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
        job.add("keyword matched", getKeywordMatched(booleanCondition.getBooleanConditionEnum(), booleanCondition.getKeywordMatched()));
        return job;
    }

    private static String getConditionalExpressionName(BooleanCondition.BooleanConditionEnum condition, String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("condition.name." + condition.name());
    }

    private static String getKeywordMatched(BooleanCondition.BooleanConditionEnum condition, String keyword) {
        switch (condition) {
            case isImmediatelyPrecededByANegation,                
        isImmediatelyFollowedByTimeIndication,
        isImmediatelyFollowedByANegation,
        isImmediatelyPrecededBySpecificTerm,
        isImmediatelyFollowedBySpecificTerm,
        isImmediatelyFollowedByAnOpinion,
        isPrecededBySubjectiveTerm,
        isFollowedByVerbPastTense,
        isFollowedByAPositiveOpinion,
        isImmediatelyPrecededByPositive,
        isImmediatelyPrecededByNegative,
        isImmediatelyFollowedByAPositiveOpinion,
        isImmediatelyFollowedByANegativeOpinion,
        isPrecededByOpinion,
        isPrecededByPositive,
        isPrecededBySpecificTerm,
        isFollowedBySpecificTerm,
        isInATextWithOneOfTheseSpecificTerms,
        isPrecededByStrongWord:
                return "(\"" + keyword + "\")";

            case isFirstLetterCapitalized,
        isNegationInCaps,
        isHashtagStart,
        isHashtag,
        isInHashtag,
        isHashtagPositiveSentiment,
        isHashtagNegativeSentiment,
        isAllCaps,
        isQuestionMarkAtEndOfText,
        isFirstTermOfText:
                return "";

            default:
                return "";

        }
    }

}
