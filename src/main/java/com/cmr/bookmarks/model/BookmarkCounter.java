package com.cmr.bookmarks.model;

public class BookmarkCounter {

    public static class CountResult {
        public int folderCount;
        public int bookmarkCount;

        public CountResult(int folderCount, int bookmarkCount) {
            this.folderCount = folderCount;
            this.bookmarkCount = bookmarkCount;
        }
    }

    public static CountResult countFoldersAndBookmarks(BookMarkCollection collection) {
        int folderCount = 0;
        int bookmarkCount = 0;

        folderCount += collection.getSubFolders().size();
        bookmarkCount += collection.getBookmarks().size();

        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            CountResult subFolderCounts = countFoldersAndBookmarks(subFolder);
            folderCount += subFolderCounts.folderCount;
            bookmarkCount += subFolderCounts.bookmarkCount;
        }

        return new CountResult(folderCount, bookmarkCount);
    }
}
