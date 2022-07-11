/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain.controller;

import java.util.List;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.Category.CategoryEnum;
import net.clementlevallois.umigon.model.Decision;
import net.clementlevallois.umigon.model.Document;
import net.clementlevallois.umigon.model.NGram;
import net.clementlevallois.umigon.model.NonWord;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.Term;
import net.clementlevallois.umigon.model.TextFragment;

/**
 *
 * @author LEVALLOIS
 */
public class HtmlHighlighter {

    public static String underline(Document doc) {
        StringBuilder sb = new StringBuilder();

        List<ResultOneHeuristics> resultsOfHeuristics = doc.getResultsOfHeuristics();
        List<Decision> sentimentDecisions = doc.getSentimentDecisions();
        for (Decision decision : sentimentDecisions) {
            if (decision.getHeuristicsImpacted() != null) {
                resultsOfHeuristics.add(decision.getHeuristicsImpacted());
            }
        }

        List<TextFragment> allTextFragments = doc.getAllTextFragments();
        for (TextFragment tf : allTextFragments) {
            boolean highlighted = false;
            for (ResultOneHeuristics resultOneHeuristics : resultsOfHeuristics) {
                TextFragment textFragmentInvestigated = resultOneHeuristics.getTextFragmentInvestigated();
                Category.CategoryEnum categoryEnum = resultOneHeuristics.getCategoryEnum();
                if (tf instanceof Term && textFragmentInvestigated instanceof NGram) {
                    NGram ngram = (NGram) textFragmentInvestigated;
                    for (Term term : ngram.getTerms()) {
                        if (term.getOriginalForm().equals(tf.getString()) && term.getIndexCardinal() == tf.getIndexCardinal()) {
                            highlighted = true;
                            if (categoryEnum.equals(CategoryEnum._11)) {
                                sb.append("<span class=\"user\" data-uid=\"001\">");
                                sb.append(tf.getString());
                                sb.append("</span>");
                                highlighted = true;
                            }
                            if (categoryEnum.equals(CategoryEnum._12)) {
                                sb.append("<span class=\"user\" data-uid=\"002\">");
                                sb.append(tf.getString());
                                sb.append("</span>");
                                highlighted = true;
                            }
                        }
                    }
                } else if (tf instanceof NonWord && textFragmentInvestigated instanceof NonWord) {
                    NonWord nonWord = (NonWord) textFragmentInvestigated;

                    if (nonWord.getString().equals(tf.getString()) && nonWord.getIndexCardinal() == tf.getIndexCardinal()) {
                        if (categoryEnum.equals(CategoryEnum._11)) {
                            sb.append("<span class=\"user\" data-uid=\"001\">");
                            sb.append(tf.getString());
                            sb.append("</span>");
                            highlighted = true;
                        }
                        if (categoryEnum.equals(CategoryEnum._12)) {
                            sb.append("<span class=\"user\" data-uid=\"002\">");
                            sb.append(tf.getString());
                            sb.append("</span>");
                            highlighted = true;
                        }
                    }
                }
            }
            if (!highlighted) {
                sb.append(tf.getString());
            }
        }

        return sb.toString();
    }

    public static String generateCssStyles(Document doc) {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>");
        sb.append("\n");
        sb.append("span.user { border-bottom:1px solid; display:inline-block; padding-bottom:1px; }");
        sb.append("\n");
        sb.append("span[data-uid='001'] { border-bottom-color:blue; }");
        sb.append("\n");
        sb.append("span[data-uid='002'] { border-bottom-color:red; }");
        sb.append("\n");
        sb.append("span[data-uid='003'] { border-bottom-color:orange; }");
        sb.append("\n");
        sb.append("</style>");
        sb.append("\n");
        return sb.toString();
    }
}
