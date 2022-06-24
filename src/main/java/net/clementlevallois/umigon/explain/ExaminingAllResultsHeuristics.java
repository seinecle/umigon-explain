/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.clementlevallois.umigon.model.BooleanCondition;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
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
            Collection<BooleanCondition> nonFlippedBooleanConditions = booleanConditions.stream().filter(x -> !x.getFlipped()).collect(Collectors.toList());

            if (resultOneHeuristics.getCategoryEnum().equals(CategoryEnum._10)) {
                continue;
            }
            sb.append(ExplanationOneHeuristics.getTermWasMatched(languageTag));
            sb.append(": \"");
            sb.append(resultOneHeuristics.getTokenInvestigated());
            if (nonFlippedBooleanConditions.isEmpty()) {
                return sb.append("\". ").toString();
            } else {
                sb.append("\", ").append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.and"));
                sb.append(" ");
            }
//            if (resultOneHeuristics.getTokenInvestigated().equals("fond")){
//                System.out.println("stop");
//            }

            for (BooleanCondition booleanCondition : nonFlippedBooleanConditions) {
                sb.append(ExplanationOneHeuristics.getConditionalExpressionName(booleanCondition.getBooleanConditionEnum(), languageTag));
                sb.append(" ");
                sb.append(ExplanationOneHeuristics.getKeywordMatched(booleanCondition.getBooleanConditionEnum(), booleanCondition.getKeywordMatched()));
                sb.append(". ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
