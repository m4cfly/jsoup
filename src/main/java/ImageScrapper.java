import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageScrapper {

    public static void main(String[] args) {
        String url = "https://books.toscrape.com/";

        String folderPath = "src/main/resources/images";
        String htmlFilePath = "src/main/resources/images.html";

        try {
            // Fetch the HTML content
            Document doc = Jsoup.connect(url).get();

            // Select all <img> elements
            Elements imgTags = doc.select("img");

            // Create the folder if it doesn't exist
            Path folder = Paths.get(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            // HTML content builder
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Scraped Images</title>\n</head>\n<body>\n");

            // Download each image and add to HTML content
            for (Element img : imgTags) {
                String imgUrl = img.absUrl("src");
                if (imgUrl.isEmpty()) {
                    continue;
                }

                // Get the image name from the URL
                String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

                // Download the image
                try (InputStream in = new URL(imgUrl).openStream();
                     FileOutputStream out = new FileOutputStream(folderPath + "/" + imgName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Downloaded: " + imgName);

                    // Add image to HTML content
                    htmlContent.append("<img src=\"").append(folderPath).append("/").append(imgName).append("\" alt=\"").append(imgName).append("\">\n");
                } catch (IOException e) {
                    System.err.println("Failed to download image: " + imgUrl);
                    e.printStackTrace();
                }
            }

            // Close HTML tags
            htmlContent.append("</body>\n</html>");

            // Write HTML content to file
            try (FileWriter writer = new FileWriter(htmlFilePath)) {
                writer.write(htmlContent.toString());
            }

            System.out.println("HTML file created: " + htmlFilePath);

        } catch (IOException e) {
            System.err.println("Error fetching the URL: " + url);
            e.printStackTrace();
        }
    }
}
