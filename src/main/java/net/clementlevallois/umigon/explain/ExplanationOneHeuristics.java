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
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;

/**
 *
 * @author LEVALLOIS
 */
public class ExplanationOneHeuristics {

    public static String getTermWasMatched(String languageTag) {
        return UmigonExplain.getLocaleBundle(languageTag).getString("statement.term_was_matched");
    }

    public static String getAndANumberOfConditionsWereMatched(int numberOfConditions, String languageTag) {
        switch (numberOfConditions){
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
