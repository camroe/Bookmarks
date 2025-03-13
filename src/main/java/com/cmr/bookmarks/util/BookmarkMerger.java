package com.cmr.bookmarks.util;

import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookmarkMerger {

    public static BookMarkCollection mergeCollections(BookMarkCollection collection1, BookMarkCollection collection2) {
//        BookMarkCollection mergedCollection = new BookMarkCollection();
//        mergedCollection.setFolderTitle(collection1.getFolderTitle());

        // Merge bookmarks
        List<BookMark> mergedBookmarks = new ArrayList<>(collection1.getBookmarks());
        mergedBookmarks.addAll(collection2.getBookmarks());
        Collections.sort(mergedBookmarks, Comparator.comparing(BookMark::getTitle));
//        mergedCollection.setBookmarks(mergedBookmarks);

        // Merge subfolders
        List<BookMarkCollection> mergedSubFolders = new ArrayList<>(collection1.getSubFolders());
        for (BookMarkCollection subFolder2 : collection2.getSubFolders()) {
            boolean merged = false;
            for (BookMarkCollection subFolder1 : mergedSubFolders) {
                if (subFolder1.getFolderTitle().equals(subFolder2.getFolderTitle())) {
                    BookMarkCollection mergedSubFolder = mergeCollections(subFolder1, subFolder2);
                    mergedSubFolders.remove(subFolder1);
                    mergedSubFolders.add(mergedSubFolder);
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                mergedSubFolders.add(subFolder2);
            }
        }
        Collections.sort(mergedSubFolders, Comparator.comparing(BookMarkCollection::getFolderTitle));
//        mergedCollection.setSubFolders(mergedSubFolders);

//        return mergedCollection;
        return null;
    }
}
