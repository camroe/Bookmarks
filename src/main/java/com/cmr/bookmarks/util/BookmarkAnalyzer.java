package com.cmr.bookmarks.util;

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

public class BookmarkAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(BookmarkAnalyzer.class);

    public static final int TIMEOUT = 6000;
    private static final Map<Integer, Integer> responseCodeCounts = new HashMap<>();
    private static int IOExceptionCount = 0;
    private static int ClassCastExceptionCount = 0;

    public static void analyzeBookmarks(BookMarkCollection bookmarks) {
        Set<String> uniqueUrls = new HashSet<>();
        Set<String> duplicateUrls = new HashSet<>();
        Set<String> invalidUrls = new HashSet<>();
        Set<String> validUrls = new HashSet<>();

        analyzeBookmarksRecursive(bookmarks, uniqueUrls, duplicateUrls, invalidUrls, validUrls);

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

    private static void analyzeBookmarksRecursive(BookMarkCollection bookmarks, Set<String> uniqueUrls, Set<String> duplicateUrls, Set<String> invalidUrls, Set<String> validUrls) {
        for (BookMark bookmark : bookmarks.getBookmarks()) {
            String url = bookmark.getUrl();
            if (!uniqueUrls.add(url)) {
                duplicateUrls.add(url);
                bookmark.setDuplicate(true);
            }
        }

        for (BookMarkCollection subFolder : bookmarks.getSubFolders()) {
            analyzeBookmarksRecursive(subFolder, uniqueUrls, duplicateUrls, invalidUrls, validUrls);
        }

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
    }


    private static boolean isValidUrl(String urlString) {
        try {
            logger.debug("Validating URL: " + urlString);
            URI uri = new URI(urlString);
            HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
            huc.setRequestMethod("GET");
            huc.setConnectTimeout(TIMEOUT);
            huc.setReadTimeout(TIMEOUT);
            int responseCode = huc.getResponseCode();

            responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);
            logger.debug("Response Code: " + responseCode);
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            IOExceptionCount++;
            logger.error("IOException for URL: " + urlString + "\nMessage=> " + e.getMessage());
            return false;
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
}