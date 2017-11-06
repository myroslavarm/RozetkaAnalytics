import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseCategory extends Parser{
    public static void parseCategory(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Elements el = getUrl(url).select("a.paginator-catalog-l-link");
        int num = Integer.parseInt(el.last().text());
        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            ParseCategoryPage.parseCategoryPage(pg);
        }
    }
}
