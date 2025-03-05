package com.cmr.bookmarks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DashAnalysis {

    public static final int TIMEOUT = 3000;
    private static final Map<Integer, Integer> responseCodeCounts = new HashMap<>();
    private static int IOExceptionCount = 0;
    private static int ClassCastExceptionCount = 0;

    public static void main(String[] args) throws IOException {

        // Set the proxy settings
//        System.setProperty("http.proxyHost", "internet.ford.com");
//        System.setProperty("http.proxyPort", "83");
//        System.setProperty("https.proxyHost", "internet.ford.com");
//        System.setProperty("https.proxyPort", "83");

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
            if (!hasDashString(url)) {
                validUrls.add(url);
            } else {
                invalidUrls.add(url);
            }
        }
        int totalBookmarks = links.size();
        int duplicateBookmarks = duplicateUrls.size();
        int validBookmarks = validUrls.size();
        int invalidBookmarks = invalidUrls.size();

        System.out.println("Total Bookmarks: " + totalBookmarks);
        System.out.println("Duplicate Bookmarks: " + duplicateBookmarks);
        System.out.println("No Dash Bookmarks: " + validBookmarks);
        System.out.println("Dash Bookmarks: " + invalidBookmarks);
        // Print the response code counts
        for (Map.Entry<Integer, Integer> entry : responseCodeCounts.entrySet()) {
            System.out.println("Response Code: " + entry.getKey() + "-" + HttpResponseMessages.getMessage(entry.getKey()) + ": Count: " + entry.getValue());
        }
        System.out.println("IOException Count: " + IOExceptionCount);
        System.out.println("ClassCastException Count: " + ClassCastExceptionCount);
    }

    public static boolean hasDashString(String url) {
        return url.contains(" - ");
    }
}