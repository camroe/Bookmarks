package com.cmr.bookmarks;

import com.cmr.bookmarks.model.*;
import com.cmr.bookmarks.util.BookMarkWriter;
import com.cmr.bookmarks.util.BookmarkAnalyzer2;
import com.cmr.bookmarks.util.BookmarkMerger;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Parse implements CommandLineRunner {
    @Autowired
    private FileNamesConfig fileNamesConfig;
    private static final Logger logger = LoggerFactory.getLogger(Parse.class);
//    public static final String BOOKMARKS_HTML = "src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html";
//    public static final String BOOKMARKS_HTML = "src/main/resources/data/bookmarks_3_25_25_BookMarks-Bar-Imac.html";
    private List<BookMarkCollection> collections = new ArrayList<>();
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
        List<String> fileNames = fileNamesConfig.getFileNames();
        for
        (String fileName : fileNames) {
            logger.info("Processing file: {}", fileName);

            try {
                File input = new File(fileName);
                Document doc = Jsoup.parse(input, "UTF-8");
                Element rootElement = doc.selectFirst("DL");
                BookMarkCollection rootCollection = new BookMarkCollection("root", 0,0);
                logger.debug("Number of children: {}", Objects.requireNonNull(rootElement).children().size());
                parseBookmarks(rootElement, rootCollection);
                BookmarkCounter.CountResult countResult = BookmarkCounter.countFoldersAndBookmarks(rootCollection);
                // Print the count of folders and bookmarks - Property
                if (printCounts) {
                    logger.info("Total Folders: {}", countResult.folderCount);
                    logger.info("Total Bookmarks: {}", countResult.bookmarkCount);
                }
//                logger.info("Printing bookmarks...Before Sort");
//                if (printBookmarks) {
//                    BookmarkPrinter.printTree(rootCollection, 0);
//                }
                // Sort the bookmarks - Property
                if (sortBookmarks) {
                    BookmarkSorter.sortFoldersAndBookmarks(rootCollection);
                    logger.info("Done sorting bookmarks.");
                }
                // Print the sorted folders and bookmarks - Property
                // Validate the bookmarks - Property
                if (validateBookmarks) {
                    logger.info("Validating bookmarks... this may take a while.");
                    BookmarkAnalyzer2.analyzeTree(rootCollection);
                }
//                logger.info("Printing bookmarks...After Sort");
//                if (printBookmarks) {
//                    BookmarkPrinter.printTree(rootCollection, 0);
//                }
                // add the collection to the list of collections
                collections.add(rootCollection);
            } catch (IOException e) {
                logger.error("Error reading bookmarks file: {}", e.getMessage());
            }
        }

        if (collections.size() > 1) {
            // Merge the collections
            logger.info("Number of collections: {}", collections.size());
            for (int i = 1; i < collections.size(); i++) {
                logger.info("Merging collection {} with collection {}", i, 0);
                // Print out the size of the collections
                logger.info("Number of folders in collection {}: {}", 0, collections.get(0).getNumberOfFolders());
                logger.info("Number of bookmarks in collection {}: {}", 0, collections.get(0).getNumberOfBookmarks());
                logger.info("Number of folders in collection {}: {}", i, collections.get(i).getNumberOfFolders());
                logger.info("Number of bookmarks in collection {}: {}", i, collections.get(i).getNumberOfBookmarks());
                logger.info("___________________________________________");
                
                collections.set(0, BookmarkMerger.mergeCollections(collections.get(0), collections.get(i)));
            }
            logger.info("Number of folders in collection {}: {}", 0,collections.get(0).getNumberOfFolders());
            logger.info("Number of bookmarks in collection {}: {}", 0,collections.get(0).getNumberOfBookmarks());
        }
        try {
            logger.info("Writing bookmarks to file: {}", fileNamesConfig.getOutputFile());
            if (printBookmarks) {
                logger.info("Printing bookmarks... this may take a while.");
                BookmarkPrinter.printTree(collections.get(0), 0);
                BookmarkPrinter.printFolders(collections.get(0),0);
            }
            BookMarkWriter.writeBookmarks(collections.get(0), fileNamesConfig.getOutputFile());
        } catch (IOException e) {
            logger.error("Failed to write bookmarks to file: {}", fileNamesConfig.getOutputFile(), e);
        }
        logger.info("Done.");
    }

    private static void parseBookmarks(Element element, BookMarkCollection collection) {
        //Assume root element is a <DL> tag with a list of bookmarks and/or folders
        Elements elements = element.children();

        for (Element el : elements) {
            logger.debug("Parsing Tag: {}", el.tagName());
            if (el.tagName().equalsIgnoreCase("DT")) {
                // Assuming that the next element is a folder (H3 tag) but is this right?
                // what if the there is a bookmark next with the <A> tag?
                logger.debug("Element size: {}", el.toString().length());
                logger.debug("Element tag: {}", el.tagName());
                logger.debug("Child size: {}", el.children().size());
                Element child = el.child(0);
                logger.debug("Child tag:0 {} text: {}", child.tagName(), child.text());
                Element folderElement = el.selectFirst("H3");
                if (folderElement != null) {
                    //There is a folder here
                    logger.debug("Folder element: {}", folderElement.html());
                    String folderTitle = folderElement.text();
                    long addDate = Long.parseLong(folderElement.attr("ADD_DATE"));
                    long lastModified = Long.parseLong(folderElement.attr("LAST_MODIFIED"));
                    BookMarkCollection subCollection = new BookMarkCollection(folderTitle, addDate, lastModified);
                    logger.debug("Adding folder: {}", folderTitle);
                    // Find the next child element that is a list (DL tag)
                    Element childElement = el.selectFirst("DL");
                    if (childElement != null) {
                        logger.info("Adding subfolder '{}' to parent folder '{}'", subCollection.getFolderTitle(), collection.getFolderTitle());
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
                        String icon = bookmarkElement.attr("ICON");
                        collection.addBookmark(new BookMark(title, url, icon, addDate, lastModified));
                        logger.debug("Adding bookmark: {}", title);
                    }
                }
                // Find the next sibling element that is a list (DL tag)

            }
        }
    }
}

