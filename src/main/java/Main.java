import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String url = "https://rozetka.com.ua/ua/memory-cards/c80044/";
        Document doc = Jsoup.connect(url).get();
    }

    public static void parseCategory(String url){
        Elements el = doc.select("a.paginator-catalog-l-link");
        int num = Integer.parseInt(el.last().text());
        for(int i=0; i<num; ++i) {
            String pg = url + "page=" + Integer.toString(i+1) + "/";
            //parse_category_page(pg);
        }
    }

    public parseCategoryPage(Document doc, String url) {
        Elements tiles = doc.select("div.g-i-tile-i-title");
        tiles = doc.getElementsByTag("");
        for(int i=0; i<tiles.size(); ++i) {
            link = tile.find("a");
            parseReviews(link['href'] + 'comments/');
        }
    }

    public parseReviews(String url) {
        int num = 0;
        Elements el = doc.select("a.paginator-catalog-l-link");
        if(el.size()) {
            num = Integer.parseInt(el.last().text());
        } else el = 0;

        sentiments = []

        for i in range(num):
        pg = url + 'page={}/'.format(i + 1)
        sentiments += parse_reviews_page(pg)

        filename = 'data/' + url.split('/')[4] + '.csv'

        with open (filename, 'w')as fl:
        wr = csv.writer(fl, dialect = 'excel')
        wr.writerows(sentiments)

        print(len(sentiments), ' reviews from ', url)
    }

    public parseReviewsPage(url) {
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
