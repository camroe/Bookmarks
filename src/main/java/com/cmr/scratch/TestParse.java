package com.cmr.scratch;

import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class TestParse {

    public static final String BOOKMARKS_HTML = "src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html";

    public static void main(String[] args) {
        try {
            File input = new File(BOOKMARKS_HTML);
            Document doc = Jsoup.parse(input, "UTF-8");
            Element rootElement = doc.selectFirst("DL");
            BookMarkCollection rootCollection = new BookMarkCollection("root");
            System.out.println("Number of children: " + rootElement.children().size());
            parseBookmarks(rootElement, rootCollection);
            System.out.println("Number of bookmarks: " + rootCollection.getBookmarks().size());
        } catch (IOException e) {
            System.out.println("Error reading bookmarks file: " + e.getMessage());
        }


    }

    private static void parseBookmarks(Element element, BookMarkCollection collection) {
        //Assume root element is a <DL> tag with a list of bookmarks and/or folders
        Elements elements = element.children();

        for (Element el : elements) {
            System.out.println("Parsing Tag: " + el.tagName());
            if (el.tagName().equalsIgnoreCase("DT")) {
                // Assuming that the next element is a folder (H3 tag) but is this right?
                // what if the there is a bookmark next with the <A> tag?
                System.out.println("Element size: " + el.toString().length());
                System.out.println("Element tag: " + el.tagName());
                System.out.println("Child size: " + el.children().size());
                Element child = el.child(0);
                System.out.println("Child tag:0 " + child.tagName() + " text: " + child.text());
                Element folderElement = el.selectFirst("H3");
                if (folderElement != null) {
                    //There is a folder here
                    System.out.println("Folder element: " + folderElement.html());
                    String folderTitle = folderElement.text();
                    BookMarkCollection subCollection = new BookMarkCollection(folderTitle);
                    System.out.println("Adding folder: " + folderTitle);
                    // Find the next child element that is a list (DL tag)
                    Element childElement = el.selectFirst("DL");
                    if (childElement != null) {
                        parseBookmarks(childElement, subCollection);
                    }
                    collection.addSubFolder(subCollection);
                } else {
                    // There is a bookmark here
                    Element bookmarkElement = el.selectFirst("A");
                    if (bookmarkElement != null) {
                        String title = bookmarkElement.text();
                        String url = bookmarkElement.attr("HREF");
                        String addDate = bookmarkElement.attr("ADD_DATE");
                        String lastModified = bookmarkElement.attr("LAST_MODIFIED");
                        collection.addBookmark(new BookMark(title, url, addDate, lastModified));
                        System.out.println("Adding bookmark: " + title);
                    }
                }
                // Find the next sibling element that is a list (DL tag)

            }
        }
    }
}

