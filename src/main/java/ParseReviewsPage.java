import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParseReviewsPage extends Parser{
    public static ArrayList<Review> parseReviewsPage(String url) throws IOException {
        Elements reviews = getUrl(url).select("article.pp-review-i");
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
