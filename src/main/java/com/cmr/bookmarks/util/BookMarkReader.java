package com.cmr.bookmarks.util;

import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class BookMarkReader {

    public static BookMarkCollection readBookmarks(String filePath) throws IOException {
        File input = new File(filePath);
        Document doc = Jsoup.parse(input, "UTF-8");
        Element rootElement = doc.selectFirst("DL");
        BookMarkCollection rootCollection = new BookMarkCollection("root");
        parseBookmarks(rootElement, rootCollection);

        return rootCollection;
    }


    private static void parseBookmarks(Element element, BookMarkCollection collection) {
        // Get all child elements of the current element
        Elements elements = element.children();
        for (Element el : elements) {
            // Check if the element is a bookmark or folder (DT tag)
            if (el.tagName().equalsIgnoreCase("DT")) {
                // Check if the element is a folder (H3 tag)
                Element folderElement = el.selectFirst("H3");
                if (folderElement != null) {
                    // Create a new bookmark collection for the folder
                    String folderTitle = folderElement.text();
                    BookMarkCollection subCollection = new BookMarkCollection(folderTitle);
                    collection.addSubFolder(subCollection);
                    System.out.println("Adding folder: " + folderTitle);

                    // Find the next sibling element that is a list (DL tag)
                    // It should be the next element after the folder element
                    Element subList = el.nextElementSibling();
                    System.out.println("Handling Sublist: " + subList.toString().substring(0, 25) + "...");
                    while (subList != null && !subList.tagName().equalsIgnoreCase("DL")) {
                        System.out.println("Tag: " + subList.tagName() + " Text: " + subList.text());
                        subList = subList.nextElementSibling();
                        System.out.println("Sublist: " + subList.text());
                    }

                    // Recursively process the sublist if it exists
                    if (subList != null && subList.tagName().equalsIgnoreCase("DL")) {
                        parseBookmarks(subList, subCollection);
                    } else {
                        // Handle empty subfolder by creating an empty DL element
                        System.out.println("Creating empty DL element");
                        parseBookmarks(new Element("DL"), subCollection);
                    }
                } else {
                    // Check if the element is a bookmark (A tag)
                    Element linkElement = el.selectFirst("A");
                    if (linkElement != null) {
                        // Extract bookmark details
                        String title = linkElement.text();
                        String url = linkElement.attr("HREF");
                        long addDate = Long.parseLong(linkElement.attr("ADD_DATE"));
                        String lastModifiedStr = linkElement.attr("LAST_MODIFIED");
                        if (lastModifiedStr.isEmpty()) {
                            lastModifiedStr = "0";
                        }
                        long lastModified = Long.parseLong(lastModifiedStr);

                        // Create a new bookmark object and add it to the collection
                        BookMark bookmark = new BookMark(title, url, collection.getFolderTitle(), addDate, lastModified);
                        collection.addBookmark(bookmark);
                        System.out.println("Adding bookmark: " + title + " to folder: " + collection.getFolderTitle());
                    }
                }
            } else if (el.tagName().equalsIgnoreCase("DL")) {
                // End the current folder processing if a </DL> tag is found
                return;
            }
        }
    }
}