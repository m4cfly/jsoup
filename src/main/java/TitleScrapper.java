import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TitleScrapper {
    public static void main(String[] args) {
        String url = "https://books.toscrape.com/";

        try {
            Document document = Jsoup.connect(url).get();
            String title = document.title();
            System.out.println("Title: " + title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
