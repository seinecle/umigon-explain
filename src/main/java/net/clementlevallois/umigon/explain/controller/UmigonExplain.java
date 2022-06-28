/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
import net.clementlevallois.umigon.model.Decision;
import net.clementlevallois.umigon.model.Document;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.explain.parameters.HtmlSettings;

/**
 *
 * @author LEVALLOIS
 */
public class UmigonExplain {

    private static ResourceBundle localeBundle;
    private static final String PATHLOCALE = "net.clementlevallois.umigon.explain.resources.i18n.text";

    public static void main(String[] args) {
    }

    public static ResourceBundle getLocaleBundle(String languageTag) {
        return ResourceBundle.getBundle(PATHLOCALE, Locale.forLanguageTag(languageTag));
    }

    public static String getSentimentPlainText(Document doc, String locale) {
        if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11).isEmpty()) {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive");
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative");
        } else {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral");
        }
    }

    public static JsonObjectBuilder getSentimentJsonObject(Document doc, String locale) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11).isEmpty()) {
            return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive"));
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative"));
        } else {
            return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral"));
        }
    }

    public static String getSentimentPlainText(CategoryEnum categoryEnum, String locale) {
        switch (categoryEnum) {
            case _11:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive");
            case _12:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative");
            default:
                return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral");
        }
    }

    public static JsonObjectBuilder getSentimentJsonObject(CategoryEnum categoryEnum, String locale) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        switch (categoryEnum) {
            case _11:
                return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive"));
            case _12:
                return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.isnegative"));
            default:
                return job.add("sentiment", UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral"));
        }
    }

    public static String getExplanationsOfDecisionsPlainText(Document doc, String languageTag) {
        StringBuilder sb = new StringBuilder();
        List<Decision> decisions = doc.getSentimentDecisions();
        if (decisions == null || decisions.isEmpty()) {
            return UmigonExplain.getLocaleBundle(languageTag).getString("decision.no_decision_made");
        }
        if (decisions.size() == 1) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_decision_has_been_made"));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_number_of_decisions_have_been_made"));
        }
        sb.append(":\n");
        for (Decision decision : decisions) {
            sb.append(ExplanationOneDecision.getExplanationOneDecisionPlainText(decision, languageTag));
            sb.append(".");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getExplanationsOfDecisionsHtml(Document doc, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        List<Decision> decisions = doc.getSentimentDecisions();
        if (decisions == null || decisions.isEmpty()) {
            return sb.append("<p>")
                    .append(UmigonExplain.getLocaleBundle(languageTag).getString("decision.no_decision_made"))
                    .append("</p>")
                    .toString();
        }
        sb.append("<p>");
        if (decisions.size() == 1) {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_decision_has_been_made"));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("statement.a_number_of_decisions_have_been_made"));
        }
        sb.append("</p>");

        sb.append("<ul>");
        for (Decision decision : decisions) {
            sb.append("<li>");
            sb.append(ExplanationOneDecision.getExplanationOneDecisionHtml(decision, languageTag, htmlSettings));
            sb.append(".");
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationsOfDecisionsJsonObject(Document doc, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        List<Decision> decisions = doc.getSentimentDecisions();
        if (decisions == null || decisions.isEmpty()) {
            return job;
        }
        int i = 1;
        for (Decision decision : decisions) {
            JsonObjectBuilder explanationOneDecisionJsonObject = ExplanationOneDecision.getExplanationOneDecisionJsonObject(decision, languageTag);
            job.add(String.valueOf(i++), explanationOneDecisionJsonObject);
        }
        return job;
    }

    public static String getExplanationOfHeuristicResultsPlainText(Document doc, String languageTag) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForPositive = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11);
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForNegative = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12);
        Collection<ResultOneHeuristics> resultsHeuristics = null;
        boolean sentimentDetected = false;
        if (!allHeuristicsResultsForPositive.isEmpty()) {
            sentimentDetected = true;
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.ispositive"));
            resultsHeuristics = allHeuristicsResultsForPositive.values();
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            sentimentDetected = true;
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isnegative"));
            resultsHeuristics = allHeuristicsResultsForNegative.values();
        }
        if (sentimentDetected) {
            sb.append(" ");
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.because"));
            sb.append(" ");
            sb.append(ExaminingAllResultsHeuristics.goThroughAllResultsHeuristicsPlainText(resultsHeuristics, languageTag));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isneutral"));
        }
        return sb.toString();
    }

    public static String getExplanationOfHeuristicResultsHtml(Document doc, String languageTag, HtmlSettings htmlSettings) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForPositive = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11);
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForNegative = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12);
        Collection<ResultOneHeuristics> resultsHeuristics = null;
        boolean sentimentDetected = false;
        sb.append("<p>");
        if (!allHeuristicsResultsForPositive.isEmpty()) {
            sentimentDetected = true;
            sb.append("<span style=\"color:")
                    .append(htmlSettings.getPositiveTermColor())
                    .append("\">");
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.ispositive"));
            sb.append("</span>");
            resultsHeuristics = allHeuristicsResultsForPositive.values();
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            sentimentDetected = true;
            sb.append("<span style=\"color:")
                    .append(htmlSettings.getNegativeTermColor())
                    .append("\">");
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isnegative"));
            sb.append("</span>");
            resultsHeuristics = allHeuristicsResultsForNegative.values();
        }
        if (sentimentDetected) {
            sb.append(" ");
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("vocabulary.because"));
            sb.append(" ");
            sb.append(ExaminingAllResultsHeuristics.goThroughAllResultsHeuristicsHtml(resultsHeuristics, languageTag, htmlSettings));
        } else {
            sb.append(UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isneutral"));
        }
        sb.append("</p>");
        return sb.toString();
    }

    public static JsonObjectBuilder getExplanationOfHeuristicResultsJson(Document doc, String languageTag) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForPositive = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11);
        Map<Integer, ResultOneHeuristics> allHeuristicsResultsForNegative = doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12);
        Collection<ResultOneHeuristics> resultsHeuristics = null;
        boolean sentimentDetected = false;
        if (!allHeuristicsResultsForPositive.isEmpty()) {
            sentimentDetected = true;
            job.add("sentiment", UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.ispositive"));
            resultsHeuristics = allHeuristicsResultsForPositive.values();
        } else if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._12).isEmpty()) {
            sentimentDetected = true;
            job.add("sentiment", UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isnegative"));
            resultsHeuristics = allHeuristicsResultsForNegative.values();
        }
        if (sentimentDetected) {
            job.add("explanation heuristics", ExaminingAllResultsHeuristics.goThroughAllResultsHeuristicsJsonObject(resultsHeuristics, languageTag));
        } else {
            job.add("sentiment", UmigonExplain.getLocaleBundle(languageTag).getString("sentiment.isneutral"));
        }
        return job;
    }
}