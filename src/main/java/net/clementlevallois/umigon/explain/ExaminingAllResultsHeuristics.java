/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.BooleanCondition.BooleanConditionEnum;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
import net.clementlevallois.umigon.model.Decision;
import net.clementlevallois.umigon.model.ResultOneHeuristics;

/**
 *
 * @author LEVALLOIS
 */
public class ExaminingAllResultsHeuristics {

    public static String goThroughAllResultsHeuristics(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag) {
        StringBuilder sb = new StringBuilder();
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            List<BooleanCondition> booleanConditions = resultOneHeuristics.getBooleanConditions();
            // because we don't care to know about conditions that needed NOT to be fulfilled
            Collection<BooleanCondition> nonFlippedBooleanConditions = booleanConditions.stream().filter(x -> !x.getFlipped() & !x.getBooleanConditionEnum().equals(BooleanConditionEnum.none)).collect(Collectors.toList());

            if (resultOneHeuristics.getCategoryEnum().equals(CategoryEnum._10)) {
                continue;
            }
            sb.append(ExplanationOneHeuristics.getTokenWasMatched(resultOneHeuristics.getTypeOfToken(), languageTag));
            sb.append(": \"");

            sb.append(resultOneHeuristics.getTokenInvestigated());
            if (nonFlippedBooleanConditions.isEmpty()) {
                return sb.append("\". ").toString();
            } else {
                sb.append("\", ");
                sb.append(ExplanationOneHeuristics.getAndANumberOfConditionsWereMatched(nonFlippedBooleanConditions.size(), languageTag));
                sb.append(":\n");
            }
//            if (resultOneHeuristics.getTokenInvestigated().equals("fond")){
//                System.out.println("stop");
//            }

            for (BooleanCondition booleanCondition : nonFlippedBooleanConditions) {
                sb.append("- ");
                sb.append(ExplanationOneHeuristics.getConditionalExpressionName(booleanCondition.getBooleanConditionEnum(), languageTag).toLowerCase(Locale.forLanguageTag(languageTag)));
                if (booleanCondition.getKeywordMatched() != null && !booleanCondition.getKeywordMatched().isEmpty()) {
                    sb.append(" ");
                    sb.append(ExplanationOneHeuristics.getKeywordMatched(booleanCondition.getBooleanConditionEnum(), booleanCondition.getKeywordMatched()));
                }
                sb.append("\n");
            }
            if (sb.toString().endsWith(", ")) {
                sb = new StringBuilder(sb.substring(0, sb.length() - 2));
            }
        }
        return sb.toString();
    }
    

}
