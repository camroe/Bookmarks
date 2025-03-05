package com.cmr.bookmarks;

import com.cmr.bookmarks.model.BookMarkCollection;
import com.cmr.bookmarks.util.BookMarkReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BookmarkAnalyzer {

    public static final int TIMEOUT = 3000;
    private static final Map<Integer, Integer> responseCodeCounts = new HashMap<>();
    public static final String BOOKMARKS_HTML = "src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html";
    private static int IOExceptionCount = 0;
    private static int ClassCastExceptionCount = 0;

    public static void main(String[] args) throws IOException {

        // Set the proxy settings
//        System.setProperty("http.proxyHost", "internet.ford.com");
//        System.setProperty("http.proxyPort", "83");
//        System.setProperty("https.proxyHost", "internet.ford.com");
//        System.setProperty("https.proxyPort", "83");
        BookMarkCollection bookmarks = BookMarkReader.readBookmarks(BOOKMARKS_HTML);

        System.out.println(bookmarks.toString());
        File input = new File(BOOKMARKS_HTML);
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements links = doc.select("A");
        Set<String> uniqueUrls = new HashSet<>();
        Set<String> duplicateUrls = new HashSet<>();
        Set<String> invalidUrls = new HashSet<>();
        Set<String> validUrls = new HashSet<>();

        for (Element link : links) {
            String url = link.attr("HREF");
            if (!uniqueUrls.add(url)) {
                duplicateUrls.add(url);
            }
        }


        int count = 0;
        for (String url : uniqueUrls) {
            count++;
            if (isValidUrl(url)) {
                validUrls.add(url);
                System.out.println(count + ". " + url + " is " + ConsoleColors.WHITE_BOLD_BRIGHT + "valid." + ConsoleColors.RESET);
            } else {
                invalidUrls.add(url);
                System.out.println(count + ". " + url + " is " + ConsoleColors.RED_BOLD + "invalid." + ConsoleColors.RESET);
            }
        }
        int totalBookmarks = links.size();
        int duplicateBookmarks = duplicateUrls.size();
        int validBookmarks = validUrls.size();
        int invalidBookmarks = invalidUrls.size();

        System.out.println("Total Bookmarks: " + totalBookmarks);
        System.out.println("Duplicate Bookmarks: " + duplicateBookmarks);
        System.out.println("Valid Bookmarks: " + validBookmarks);
        System.out.println("Invalid Bookmarks: " + invalidBookmarks);
        // Print the response code counts
        for (Map.Entry<Integer, Integer> entry : responseCodeCounts.entrySet()) {
            System.out.println("Response Code: " + entry.getKey() + "-" + HttpResponseMessages.getMessage(entry.getKey()) + ": Count: " + entry.getValue());
        }
        System.out.println("IOException Count: " + IOExceptionCount);
        System.out.println("ClassCastException Count: " + ClassCastExceptionCount);
    }

    private static String cleanUrl(String urlString) {
        String originalUrlString = urlString;
        int spaceIndex = urlString.indexOf(' ');
        int hyphenIndex = urlString.indexOf('-');
        int endIndex = (spaceIndex != -1) ? spaceIndex : (hyphenIndex != -1) ? hyphenIndex : urlString.length();
        String returnString = urlString.substring(0, endIndex).trim();
        if (originalUrlString.equals(returnString)) {
            return urlString;
        } else {
            System.out.println("Cleaned URL: " + returnString);
            System.out.println("Original URL: " + originalUrlString);
        }
        return returnString;
    }

    private static boolean isValidUrl(String urlString) {
//        urlString = cleanUrl(urlString);
        try {
            System.out.println("Validating URL: " + urlString);
            URI uri = new URI(urlString);
            HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
            huc.setRequestMethod("GET"); // Changed from "HEAD" to "GET" to get the response code
            huc.setConnectTimeout(TIMEOUT);
            huc.setReadTimeout(TIMEOUT);
            int responseCode = huc.getResponseCode();

            // Update the response code count
            responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);
            System.out.println("Response Code: " + responseCode + "-" + HttpResponseMessages.getMessage(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
                String newUrl = huc.getHeaderField("Location");
                System.out.println(ConsoleColors.YELLOW + "URL has moved permanently to: " + ConsoleColors.RESET + newUrl);
                System.out.println("Validating new URL: " + newUrl);
                if (isValidUrl(newUrl)) {
                    return true;
                } else {
                    System.out.println("New URL is invalid: " + newUrl);
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            IOExceptionCount++;
            System.out.println(ConsoleColors.RED + "IOException for URL: " + ConsoleColors.RESET + urlString + "\nMessage=> " + e.getMessage());
            return false;
        } catch (ClassCastException e) {
            ClassCastExceptionCount++;
            System.out.println(ConsoleColors.RED + "Error: Exception occurred while validating URL: " + ConsoleColors.RESET + urlString);
            return false;
        } catch (URISyntaxException e) {
            System.out.println(ConsoleColors.RED + "URISyntaxException for URL: " + ConsoleColors.RESET + urlString + "\nMessage=> " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.RED + "IllegalArgumentException for URL: " + ConsoleColors.RESET + urlString + "\nMessage=> " + e.getMessage());
            return false;
        }
    }
}