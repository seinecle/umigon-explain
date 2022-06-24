/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import net.clementlevallois.umigon.model.BooleanCondition;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isAllCaps;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isFirstTermOfText;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtag;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagNegativeSentiment;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagPositiveSentiment;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isHashtagStart;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isInHashtag;
import static net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum.isQuestionMarkAtEndOfText;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneHeuristics {

    public static String getTermWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.term_was_matched");
    }

    public static String getConditionalExpressionName(BooleanCondition.BooleanConditionEnum condition, String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("condition.name." + condition.name());
    }

    public static String getKeywordMatched(BooleanCondition.BooleanConditionEnum condition, String keyword) {
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
