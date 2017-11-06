import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ParseReviews extends Parser{
    public static void parseReviews(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        int num = 0;
        Elements nums = getUrl(url).select("a.paginator-catalog-l-link");
        if(nums.size()>0) {
            num = Integer.parseInt(nums.last().text());
        } else num = 0;

        ArrayList<Review> sentiments = new ArrayList<>();

        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            sentiments.addAll((ParseReviewsPage.parseReviewsPage(pg)));
        }

        String filename = "data/" + url.split("/")[4] + ".csv";
        Writer writer = new FileWriter(filename);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(sentiments);
        writer.close();
    }
}
