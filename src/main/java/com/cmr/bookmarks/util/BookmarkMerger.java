package com.cmr.bookmarks.util;

import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;

import java.util.*;

public class BookmarkMerger {

    private BookmarkMerger() {
        // Private constructor to prevent instantiation
    }
    /**
     * Merges two bookmark collections into one, ensuring no duplicates and maintaining the structure.
     *
     * @param collection1 The first bookmark collection.
     * @param collection2 The second bookmark collection.
     * @return A new merged bookmark collection.
     */
    public static BookMarkCollection mergeCollections(BookMarkCollection collection1, BookMarkCollection collection2) {
        // Create a new collection for the merged result
        BookMarkCollection mergedCollection = new BookMarkCollection(
                collection1.getFolderTitle(),
                Math.min(collection1.getAddDate(), collection2.getAddDate()),
                Math.max(collection1.getLastModified(), collection2.getLastModified())
        );

        // Merge bookmarks, ensuring no duplicates
        Set<BookMark> mergedBookmarksSet = new HashSet<>(collection1.getBookmarks());
        mergedBookmarksSet.addAll(collection2.getBookmarks());
        List<BookMark> mergedBookmarks = new ArrayList<>(mergedBookmarksSet);
        mergedBookmarks.sort(Comparator.comparing(BookMark::getTitle, String.CASE_INSENSITIVE_ORDER));
        mergedCollection.setBookmarks(mergedBookmarks);

        // Merge subfolders recursively
        List<BookMarkCollection> mergedSubFolders = new ArrayList<>(collection1.getSubFolders());
        for (BookMarkCollection subFolder2 : collection2.getSubFolders()) {
            boolean merged = false;
            for (int i = 0; i < mergedSubFolders.size(); i++) {
                BookMarkCollection subFolder1 = mergedSubFolders.get(i);
                if (subFolder1.getFolderTitle().equalsIgnoreCase(subFolder2.getFolderTitle())) {
                    mergedSubFolders.set(i, mergeCollections(subFolder1, subFolder2));
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                mergedSubFolders.add(subFolder2);
            }
        }
        mergedSubFolders.sort(Comparator.comparing(BookMarkCollection::getFolderTitle, String.CASE_INSENSITIVE_ORDER));
        mergedCollection.setSubFolders(mergedSubFolders);

        return mergedCollection;
    }
}

