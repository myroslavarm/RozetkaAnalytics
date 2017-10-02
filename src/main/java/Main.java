import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "https://rozetka.com.ua/ua/memory-cards/c80044/";
        parseCategory(url);
    }

    public static void parseCategory(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Document doc = Jsoup.connect(url).get();
        Elements el = doc.select("a.paginator-catalog-l-link");
        int num = Integer.parseInt(el.last().text());
        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            parseCategoryPage(pg);
        }
    }

    public static void parseCategoryPage(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Document doc = Jsoup.connect(url).get();
        Elements tiles = doc.select("div.g-i-tile-i-title");
        for(Element tile: tiles) {
            Elements link = tile.select("a");
            System.out.println(link.attr("href") + "comments/");
            parseReviews(link.attr("href") + "comments/");
        }
    }

    public static void parseReviews(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Document doc = Jsoup.connect(url).get();
        int num = 0;
        Elements nums = doc.select("a.paginator-catalog-l-link");
        if(nums.size()>0) {
            num = Integer.parseInt(nums.last().text());
        } else num = 0;

        ArrayList<Review> sentiments = new ArrayList<>();

        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            sentiments.addAll((parseReviewsPage(pg)));
        }

        String filename = "data/" + url.split("/")[4] + ".csv";
        Writer writer = new FileWriter(filename);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(sentiments);
        writer.close();
    }

    public static ArrayList<Review> parseReviewsPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements reviews = doc.select("article.pp-review-i");
        ArrayList<Review> sentiments = new ArrayList<>();

        for(Element review: reviews) {
            Elements star = review.select("span.g-rating-stars-i");
            Elements text = review.select("div.pp-review-text");

            if(star.size()>0) {
                sentiments.add(new Review(text.text(), Integer.parseInt(star.get(0).attr("content"))));
            }
        }
        return(sentiments);
    }
}
