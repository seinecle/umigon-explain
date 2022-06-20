/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.explain;

import java.util.Locale;
import java.util.ResourceBundle;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.Document;

/**
 *
 * @author LEVALLOIS
 */
public class UmigonExplain {

    private static ResourceBundle localeBundle;
    private static final String PATHLOCALE = "net.clementlevallois.umigon.explain.resources.i18n.text";

    public static void main (String [] args){
    }

    
    public static ResourceBundle getLocaleBundle(String languageTag){
        return ResourceBundle.getBundle(PATHLOCALE, Locale.forLanguageTag(languageTag));
    }
    
    public static String getSentiment(Document doc, String locale){
        if (!doc.getAllHeuristicsResultsForOneCategory(Category.CategoryEnum._11).isEmpty()){
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.ispositive");
        } else {
            return UmigonExplain.getLocaleBundle(locale).getString("sentiment.isneutral");
            
        }
    }
}
