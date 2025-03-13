package com.cmr.bookmarks.util;

import com.cmr.bookmarks.ConsoleColors;
import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BookmarkAnalyzer2 {
    private static final Logger logger = LoggerFactory.getLogger(BookmarkAnalyzer2.class);

    public static final int TIMEOUT = 3000;
    private static final Map<Integer, Integer> responseCodeCounts = new HashMap<>();
    private static int IOExceptionCount = 0;
    private static int ClassCastExceptionCount = 0;
    private static Set<String> uniqueUrls = new HashSet<>();
    private static Set<String> duplicateUrls = new HashSet<>();
    private static Set<String> invalidUrls = new HashSet<>();
    private static Set<String> validUrls = new HashSet<>();

    public static void analyzeTree(BookMarkCollection collection) {
        analyzeTreeRecurse(collection);

        int count = 0;
        for (String url : uniqueUrls) {
            count++;
            if (isValidUrl(url)) {
                validUrls.add(url);
                logger.info(count + ". " + url + " is valid.");
            } else {
                invalidUrls.add(url);
                logger.info(count + ". " + url + " is invalid.");
            }
        }
        outputMetrics();
    }

    private static void analyzeTreeRecurse(BookMarkCollection collection) {
        // Print the folder name
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + collection.getFolderTitle() + ConsoleColors.RESET);

        // Analyze the bookmarks
        for (BookMark bookmark : collection.getBookmarks()) {
//        //    System.out.println(bookmark.getTitle());
            String url = bookmark.getUrl();
            if (!uniqueUrls.add(url)) {
                duplicateUrls.add(url);
                bookmark.setDuplicate(true);
            }
        }

        // Recursively analyze the subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            analyzeTreeRecurse(subFolder);
        }
    }

    private static void outputMetrics() {
        //Output the analysis
        int totalBookmarks = uniqueUrls.size() + duplicateUrls.size();
        int duplicateBookmarks = duplicateUrls.size();
        int validBookmarks = validUrls.size();
        int invalidBookmarks = invalidUrls.size();

        logger.info("Total Bookmarks: " + totalBookmarks);
        logger.info("Duplicate Bookmarks: " + duplicateBookmarks);
        logger.info("Valid Bookmarks: " + validBookmarks);
        logger.info("Invalid Bookmarks: " + invalidBookmarks);
        for (Map.Entry<Integer, Integer> entry : responseCodeCounts.entrySet()) {
            logger.debug("Response Code: " + entry.getKey() + ": Count: " + entry.getValue());
        }
        logger.info("IOException Count: " + IOExceptionCount);
        logger.info("ClassCastException Count: " + ClassCastExceptionCount);
    }

    //    private static boolean isValidUrl(String urlString) {
//        try {
//            logger.debug("Validating URL: " + urlString);
//            URI uri = new URI(urlString);
//            HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
//            huc.setRequestMethod("GET");
//            huc.setConnectTimeout(TIMEOUT);
//            huc.setReadTimeout(TIMEOUT);
//            int responseCode = huc.getResponseCode();
//
//            responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);
//            logger.info("Response Code: " + responseCode + " for URL " + urlString);
//            return responseCode == HttpURLConnection.HTTP_OK;
//        } catch (IOException e) {
//            IOExceptionCount++;
//            logger.error("IOException for URL: " + urlString + "\nMessage=> " + e.getMessage());
//            logger.error(e.toString());
//            return false;
//        } catch (ClassCastException e) {
//            ClassCastExceptionCount++;
//            logger.error("Error: Exception occurred while validating URL: " + urlString);
//            return false;
//        } catch (URISyntaxException e) {
//            logger.error("URISyntaxException for URL: " + urlString + "\nMessage=> " + e.getMessage());
//            return false;
//        } catch (IllegalArgumentException e) {
//            logger.error("IllegalArgumentException for URL: " + urlString + "\nMessage=> " + e.getMessage());
//            return false;
//        }
//    }
    private static boolean isValidUrl(String urlString) {
        int maxRetries = 3;
        int retryDelayMillis = 2000; // 2 seconds

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                logger.debug("Validating URL (attempt " + attempt + "): " + urlString);
                URI uri = new URI(urlString);
                HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
                huc.setRequestMethod("GET");
                huc.setConnectTimeout(TIMEOUT);
                huc.setReadTimeout(TIMEOUT);
                int responseCode = huc.getResponseCode();

                responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);
                logger.info("Response Code: " + responseCode + " for URL " + urlString);
                return responseCode == HttpURLConnection.HTTP_OK;

            } catch (IOException e) {
                IOExceptionCount++;
                logger.error("IOException for URL: " + urlString + "\nMessage=> " + e.getMessage() + " (attempt " + attempt + ")");
                logger.error(e.toString());
                if (attempt < maxRetries && e instanceof java.net.UnknownHostException) {
                    try {
                        Thread.sleep(retryDelayMillis); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt(); // Restore interrupted state
                    }
                } else {
                    return false; // Give up after max retries or for non-UnknownHostException
                }
            } catch (ClassCastException e) {
                ClassCastExceptionCount++;
                logger.error("Error: Exception occurred while validating URL: " + urlString);
                return false;
            } catch (URISyntaxException e) {
                logger.error("URISyntaxException for URL: " + urlString + "\nMessage=> " + e.getMessage());
                return false;
            } catch (IllegalArgumentException e) {
                logger.error("IllegalArgumentException for URL: " + urlString + "\nMessage=> " + e.getMessage());
                return false;
            }
        }
        return false; // Return false if all retries fail
    }

}

