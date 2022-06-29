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
        sb.append(" ");
        if (!resultsHeuristics.isEmpty()) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.because"));
            sb.append(" ");
            for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
                sb.append(ExplanationOneHeuristics.getOneHeuristicsResultsPlainText(resultOneHeuristics, languageTag));
            }
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.because_no_heuristics_matched"));
        }
        return sb.toString();
    }

    public static String goThroughAllResultsHeuristicsHtml(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        if (!resultsHeuristics.isEmpty()) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.because"));
            sb.append(" ");
            for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
                sb.append(ExplanationOneHeuristics.getOneHeuristicsResultsHtml(resultOneHeuristics, languageTag, htmlSettings));
            }
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.because_no_heuristics_matched"));
        }
        sb.append("<br/>");
        return sb.toString();
    }

    public static JsonObjectBuilder goThroughAllResultsHeuristicsJsonObject(Collection<ResultOneHeuristics> resultsHeuristics, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        int i = 1;
        for (ResultOneHeuristics resultOneHeuristics : resultsHeuristics) {
            job.add("token matched #" + String.valueOf(i++), ExplanationOneHeuristics.getOneHeuristicsResultsJsonObject(resultOneHeuristics, languageTag));
        }
        return job;
    }

}
