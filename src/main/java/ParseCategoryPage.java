import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseCategoryPage extends Parser {
    public static void parseCategoryPage(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Elements tiles = getUrl(url).select("div.g-i-tile-i-title");
        for(Element tile: tiles) {
            Elements link = tile.select("a");
            System.out.println(link.attr("href") + "comments/");
            ParseReviews.parseReviews(link.attr("href") + "comments/");
        }
    }
}
