package com.cmr.bookmarks.model;

import java.util.Collections;
import java.util.Comparator;

public class BookmarkSorter {

    public static void sortFoldersAndBookmarks(BookMarkCollection collection) {
        // Sort subfolders alphabetically by name
        collection.getSubFolders().sort(Comparator.comparing(BookMarkCollection::getFolderTitle, String.CASE_INSENSITIVE_ORDER));

        // Sort bookmarks alphabetically by title
        collection.getBookmarks().sort(Comparator.comparing(BookMark::getTitle, String.CASE_INSENSITIVE_ORDER));

        // Recursively sort subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            sortFoldersAndBookmarks(subFolder);
        }
    }
}
