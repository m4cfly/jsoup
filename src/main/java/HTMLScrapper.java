import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HTMLScrapper {
    public static void main(String[] args) {
        //fetch website
        String url = "https://books.toscrape.com/";


        //break down site
        try {
            Document document = Jsoup.connect(url).get();
            //fetch  A LINKS
            Elements links = document.select("a[href]");
            for (Element link : links) {
                String href = link.attr("href");
                String text = link.text();
                System.out.println(text + ": " + href);
            }
            // clean DIRTY HTML
            String dirtyHtml = "<div><p>Unclosed tag <a href='#'>link";
            String dirtyWeb = url;
            Document cleanDocument1 = Jsoup.parse(url);
            Document cleanDocument = Jsoup.parse(dirtyHtml);
            String cleanedHtml = cleanDocument.html();
            String cleanedWeb = cleanDocument1.html();
            System.out.println("dirty WEB: + " + dirtyWeb);
            System.out.println("cleaned WEB: " + cleanedWeb);
            System.out.println("dirty HTML: + " + dirtyHtml);
            System.out.println("cleaned html: " + cleanedHtml);
//          HANDLE REDIRECTS AND USER AGENT
//          Document documentUSERAGENTREDIRECTS = Jsoup.connect("https://example.com")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
//                    .followRedirects(true)
//                    .get();


            String title = document.title();
            String body = document.text();
            String footer = document.location();
            //System.out.println("Title: " + title);
            //System.out.println("Body: " + body);
            //System.out.println("Footer: " + footer);

            // PARSE from string
            String html = "<html><head><title>Sample Title</title></head><body><p>Sample content</p></body></html>";
            Document documentFromString = Jsoup.parse(html);
            System.out.println("PARSING FROM STRING: " + documentFromString);

            //PARSE from a file
            File input = new File("src/main/resources/index.html");
            Document documentFromFile = Jsoup.parse(input, "UTF-8");
            System.out.println("PARSING FROM FILE: " + documentFromFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch the document.");
        }
    }
}
