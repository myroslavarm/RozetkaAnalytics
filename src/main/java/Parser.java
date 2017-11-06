import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Parser {
    public static Document getUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc;
    }
}
