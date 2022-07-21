package net.clementlevallois.umigon.explain.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import net.clementlevallois.umigon.classifier.sentiment.ClassifierSentimentOneDocument;
import net.clementlevallois.umigon.model.Document;
import net.clementlevallois.umigon.semantics.resources.Semantics;
import net.clementlevallois.umigon.explain.controller.UmigonExplain;
import net.clementlevallois.umigon.explain.parameters.HtmlSettings;

/**
 *
 * @author LEVALLOIS
 */
public class ControllerForTestsSentiment {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        HtmlSettings htmlSettings = new HtmlSettings();
        
        Semantics semanticsEN = new Semantics();
        semanticsEN.loader("en");

        Semantics semanticsFR = new Semantics();
        semanticsFR.loader("fr");


        ClassifierSentimentOneDocument classifierOneDocument = new ClassifierSentimentOneDocument(semanticsFR);
        Document doc;
        Document result;
        Document resultEN;
        Document docEN = new Document();

        ClassifierSentimentOneDocument classifierOneDocumentEN = new ClassifierSentimentOneDocument(semanticsEN);

        docEN = new Document();
        docEN.setText("Nuts have properties)");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        String markers = UmigonExplain.getExplanationOfHeuristicResultsHtml(resultEN, "fr", htmlSettings);
        System.out.println("semantic markers found: " + markers);
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsHtml(resultEN, "fr", htmlSettings));

        System.out.println("expected: neutral tone");
        System.out.println("-----------");
        
        docEN = new Document();
        docEN.setText("this is baaaad :-)");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsHtml(resultEN, "fr", htmlSettings));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsHtml(resultEN, "fr", htmlSettings));

        System.out.println("expected: positive tone");
        System.out.println("-----------");
        
        docEN.setText("nocode is not horrible");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsHtml(resultEN, "fr", htmlSettings));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsHtml(resultEN, "fr", htmlSettings));

        System.out.println("expected: neutral tone");
        System.out.println("-----------");
        
        docEN = new Document();
        docEN.setText("I am fond of nocode functions");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(resultEN, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(resultEN, "fr"));
        System.out.println("expected: positive tone");
        System.out.println("-----------");

        docEN = new Document();
        docEN.setText("I am not glad of this");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(resultEN, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(resultEN, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        docEN = new Document();
        docEN.setText("I am fond of coding but I prefer nocode functions");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(resultEN, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(resultEN, "fr"));
        System.out.println("expected: neutral tone");
        System.out.println("-----------");

        docEN = new Document();
        docEN.setText("Men and women, our defenders! You are brilliantly defending the country from one of the most powerful countries in the world.");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(resultEN, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(resultEN, "fr"));
        System.out.println("expected: positive tone");
        System.out.println("-----------");

        docEN = new Document();
        docEN.setText("According to preliminary data, unfortunately, we have lost 137 of our heroes today - our citizens. 10 of them are officers. 316 are wounded");
        resultEN = classifierOneDocumentEN.call(docEN);
        System.out.println("test: " + docEN.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(resultEN, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(resultEN, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(resultEN, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");        
        
        doc = new Document();
        doc.setText("J'exprime une opinion sur cet élu Marcel sauras tu la deviner #Marceldémission");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");
        
        
        doc = new Document();
        doc.setText("j'en ai marre de la situation");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        
        doc = new Document();
        doc.setText("#Elections #Elections2020#Municipales #Municipales2020ElectionsMunicipales ElectionsMunicipales2020https://t.co/Lr9rGG71M4");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: neutral tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Il est gentil, pour être poli");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsHtml(result, "fr", htmlSettings));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsHtml(result, "fr", htmlSettings));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("La meuf qui hurle dans le bus parce qu' on s' est assis à côté d' elle... 😒");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Miss France comme chaque année! Tant qu'ils mettrons ce programme CONTRE le téléthon!");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Miss France comme chaque année! Tant qu'ils mettrons ce programme contre le téléthon!");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: neutral tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Nocode c'est tendance :)");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsHtml(result, "fr", htmlSettings));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Il y a de vraies burnes");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: negative tone");
        System.out.println("-----------");

        doc = new Document();
        doc.setText("Il a de vraies burnes");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: positive tone");
        System.out.println("-----------");


        doc = new Document();
        doc.setText("@Marieadelaided3 Bonsoir. C'est toujours un plaisir de lire vos messages et critiques. Mais une nouvelle fois, pouvons nous faire quelque chose pour vous ?");
        result = classifierOneDocument.call(doc);
        System.out.println("test: " + doc.getText());
        System.out.println("result: " + UmigonExplain.getSentimentPlainText(result, "fr"));
        System.out.println("semantic markers found: " + UmigonExplain.getExplanationOfHeuristicResultsPlainText(result, "fr"));
        System.out.println("decisions made: " + UmigonExplain.getExplanationsOfDecisionsPlainText(result, "fr"));
        System.out.println("expected: positive tone");
        System.out.println("-----------");


        
//        ClassifierSentimentOneWordInADocument oneWordInADocument = new ClassifierSentimentOneWordInADocument(semanticsEN);
//        String resultString = oneWordInADocument.call("horrible", "nocode is not horrible :-)");
//        System.out.println("test one word \"horrible\" in: nocode is not horrible :-)");
//        System.out.println("result: " + resultString);
//        System.out.println("expected: neutral tone");
    }

}
