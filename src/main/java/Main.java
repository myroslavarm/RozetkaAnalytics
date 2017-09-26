import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "https://rozetka.com.ua/ua/memory-cards/c80044/";
        parseCategory(url);
    }

    public static void parseCategory(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements el = doc.select("a.paginator-catalog-l-link");
        int num = Integer.parseInt(el.last().text());
        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            parseCategoryPage(pg);
        }
    }

    public static void parseCategoryPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements tiles = doc.select("div.g-i-tile-i-title");
        for(Element tile: tiles) {
            Elements link = tile.select("a");
            parseReviews(link.select("href") + "comments/");
        }
    }

    public static void parseReviews(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        int num = 0;
        Elements nums = doc.select("a.paginator-catalog-l-link");
        if(nums.size()>0) {
            num = Integer.parseInt(nums.last().text());
        } else num = 0;

        String[] sentiments = new String[num];

        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            sentiments += parseReviewsPage(pg);
        }

        String filename = "data/" + url.split("/")[4] + ".csv";
        PrintWriter pw = new PrintWriter(new File(filename));
        StringBuilder sb = new StringBuilder();
        sb.append(sentiments);
        pw.write(sb.toString() + " reviews from " + url);
        pw.close();
    }

    public static void parseReviewsPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements reviews = doc.select("article.pp-review-i");
        String[] sentiments = new String[reviews.size()];

        for(Element review: reviews) {
            Elements star = review.select("span.g-rating-stars-i");
            Elements text = review.select("div.pp-review-text");
            if(star>0) {
                Elements texts = text.select("div.pp-review-text-i");
                sentiments.add((star.select("content"), Integer.parseInt(texts.first().text()));
            }
        }

        return sentiments[];
    }
}
