package com.cmr.bookmarks.model;

import java.util.Collections;
import java.util.Comparator;

public class BookmarkSorter {

    public static void sortFoldersAndBookmarks(BookMarkCollection collection) {
        // Sort subfolders alphabetically by name
        Collections.sort(collection.getSubFolders(), Comparator.comparing(BookMarkCollection::getFolderTitle));

        // Sort bookmarks alphabetically by title
        Collections.sort(collection.getBookmarks(), Comparator.comparing(BookMark::getTitle));

        // Recursively sort subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            sortFoldersAndBookmarks(subFolder);
        }
    }
}
