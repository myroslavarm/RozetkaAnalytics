import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;

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

        with open (filename, 'w')as fl:
        wr = csv.writer(fl, dialect = 'excel')
        wr.writerows(sentiments)

        print(len(sentiments), ' reviews from ', url)
    }

    public static void parseReviewsPage(String url) {
        html_doc = urllib.request.urlopen(url)
        soup = BeautifulSoup(html_doc, 'html.parser')
        reviews = soup.find_all('article', class_ = 'pp-review-i')
        sentiments = []

        for review in reviews:
        star = review.find('span', class_ = 'g-rating-stars-i')
        text = review.find('div', class_ = 'pp-review-text')
        if star:
        texts = text.find_all('div', class_ = 'pp-review-text-i')
        sentiments.append([star['content'], texts[0].get_text().strip()])

        return sentiments;
    }
}
