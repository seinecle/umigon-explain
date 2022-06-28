/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Collection;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.explain.parameters.HtmlSettings;

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

    public static String goThroughAllResultsHeuristicsHtml(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            sb.append(ExplanationOneHeuristics.getOneHeuristicsResultsHtml(resultOneHeuristics, languageTag, htmlSettings));
        }
        sb.append("<br/>");
        return sb.toString();
    }

    public static JsonObjectBuilder goThroughAllResultsHeuristicsJsonObject(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        int i = 1;
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            job.add(String.valueOf(i++), ExplanationOneHeuristics.getOneHeuristicsResultsJsonObject(resultOneHeuristics, languageTag));
        }
        return job;
    }

}
