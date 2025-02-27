package com.cmr.bookmarks;

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
    private static int IOExceptionCount = 0;
    private static int ClassCastExceptionCount = 0;

    public static void main(String[] args) throws IOException {

        // Set the proxy settings
        System.setProperty("http.proxyHost", "internet.ford.com");
        System.setProperty("http.proxyPort", "83");
        System.setProperty("https.proxyHost", "internet.ford.com");
        System.setProperty("https.proxyPort", "83");

        File input = new File("src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html");
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
                System.out.println(count + ". " + url + " is valid.");
            } else {
                invalidUrls.add(url);
                System.out.println(count + ". " + url + " is invalid.");
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
        int spaceIndex = urlString.indexOf(' ');
        int hyphenIndex = urlString.indexOf('-');
        int endIndex = (spaceIndex != -1) ? spaceIndex : (hyphenIndex != -1) ? hyphenIndex : urlString.length();
        return urlString.substring(0, endIndex).trim();
    }

    private static boolean isValidUrl(String urlString) {
        urlString = cleanUrl(urlString);
        try {
            System.out.println("Validating URL: " + urlString);
            URI uri = new URI(urlString);
            System.out.println("Constructing url connection");
            HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
            System.out.println("Constructing url connection");
            huc.setRequestMethod("GET"); // Changed from "HEAD" to "GET" to get the response code
            huc.setConnectTimeout(TIMEOUT);
            huc.setReadTimeout(TIMEOUT);
            int responseCode = huc.getResponseCode();

            // Update the response code count
            responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);


            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            IOExceptionCount++;
            System.err.println("IOException for URL: " + urlString + " - " + e.getMessage());
            return false;
        } catch (ClassCastException e) {
            ClassCastExceptionCount++;
            System.out.println("Error: Exception occurred while validating URL: " + urlString);
            return false;
        } catch (URISyntaxException e) {
            System.err.println("URISyntaxException for URL: " + urlString + " - " + e.getMessage());
            return false;
        }
    }
}