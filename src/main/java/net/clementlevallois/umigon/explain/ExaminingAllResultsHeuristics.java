/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Collection;
import net.clementlevallois.umigon.model.ResultOneHeuristics;

/**
 *
 * @author LEVALLOIS
 */
public class ExaminingAllResultsHeuristics {

    public static String goThroughAllResultsHeuristicsPlainText(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag) {
        StringBuilder sb = new StringBuilder();
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            sb.append(ExplanationOneHeuristics.getOneHeuristicsResultsPlainText(resultOneHeuristics, languageTag));
        }
        return sb.toString();
    }
    

    public static JsonObjectBuilder goThroughAllResultsHeuristicsJsonObject(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            job.addAll(ExplanationOneHeuristics.getOneHeuristicsResultsJsonObject(resultOneHeuristics, languageTag));
        }
        return job;
    }
    

}
