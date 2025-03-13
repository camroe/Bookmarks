package com.cmr.scratch;

import com.cmr.bookmarks.model.*;
import com.cmr.bookmarks.util.BookmarkAnalyzer2;
import com.cmr.bookmarks.util.SystemPropertiesPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Parse implements CommandLineRunner {
    @Autowired
    private FileNamesConfig fileNamesConfig;
    private static final Logger logger = LoggerFactory.getLogger(Parse.class);
    public static final String BOOKMARKS_HTML = "src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html";

    @Value("${com.cmr.bookmarks.model.sort}")
    private boolean sortBookmarks;
    @Value("${com.cmr.bookmarks.model.print}")
    private boolean printBookmarks;
    @Value("${com.cmr.bookmarks.model.validate}")
    private boolean validateBookmarks;
    @Value("${com.cmr.bookmarks.model.printCounts}")
    private boolean printCounts;
    @Value("${com.cmr.bookmarks.model.printProperties}")
    private boolean printProperties;
    public static void main(String[] args) {
        SpringApplication.run(Parse.class, args);
    }


    @Override
    public void run(String... args) {

        if (printProperties) {
            SystemPropertiesPrinter.printSystemProperties();
        }

        try {
            File input = new File(BOOKMARKS_HTML);
            Document doc = Jsoup.parse(input, "UTF-8");
            Element rootElement = doc.selectFirst("DL");
            BookMarkCollection rootCollection = new BookMarkCollection("root");
            logger.debug("Number of children: " + rootElement.children().size());
            parseBookmarks(rootElement, rootCollection);
            BookmarkCounter.CountResult countResult = BookmarkCounter.countFoldersAndBookmarks(rootCollection);
            // Print the count of folders and bookmarks - Property
            if (printCounts) {
                logger.info("Total Folders: {}", countResult.folderCount);
                logger.info("Total Bookmarks: {}", countResult.bookmarkCount);
            }


            // Sort the bookmarks - Property
            if (sortBookmarks) {
                BookmarkSorter.sortFoldersAndBookmarks(rootCollection);
                logger.info("Done sorting bookmarks.");
            }
            // Print the sorted folders and bookmarks - Property
            if (printBookmarks) {
                BookmarkPrinter.printTree(rootCollection, 0);
            }
            // Validate the bookmarks - Property
            if (validateBookmarks) {
                logger.info("Validating bookmarks... this may take a while.");
//                BookmarkAnalyzer.analyzeBookmarks(rootCollection);
                BookmarkAnalyzer2.analyzeTree(rootCollection);
            }
        } catch (IOException e) {
            logger.error("Error reading bookmarks file: " + e.getMessage());
        }
        logger.info("Done.");
    }

    private static void parseBookmarks(Element element, BookMarkCollection collection) {
        //Assume root element is a <DL> tag with a list of bookmarks and/or folders
        Elements elements = element.children();

        for (Element el : elements) {
            logger.debug("Parsing Tag: " + el.tagName());
            if (el.tagName().equalsIgnoreCase("DT")) {
                // Assuming that the next element is a folder (H3 tag) but is this right?
                // what if the there is a bookmark next with the <A> tag?
                logger.debug("Element size: " + el.toString().length());
                logger.debug("Element tag: " + el.tagName());
                logger.debug("Child size: " + el.children().size());
                Element child = el.child(0);
                logger.debug("Child tag:0 " + child.tagName() + " text: " + child.text());
                Element folderElement = el.selectFirst("H3");
                if (folderElement != null) {
                    //There is a folder here
                    logger.debug("Folder element: " + folderElement.html());
                    String folderTitle = folderElement.text();
                    BookMarkCollection subCollection = new BookMarkCollection(folderTitle);
                    logger.debug("Adding folder: " + folderTitle);
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
                        logger.debug("Adding bookmark: " + title);
                    }
                }
                // Find the next sibling element that is a list (DL tag)

            }
        }
    }
}

