package com.cmr.bookmarks.model;

import java.util.ArrayList;
import java.util.List;

public class BookMarkCollection {
    private String folderTitle;
    private List<BookMark> bookmarks;
    private List<BookMarkCollection> subFolders;

    public BookMarkCollection(String folderTitle) {
        this.folderTitle = folderTitle;
        this.bookmarks = new ArrayList<>();
        this.subFolders = new ArrayList<>();
    }

    public String getFolderTitle() {
        return folderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    public void addBookmark(BookMark bookmark) {
        this.bookmarks.add(bookmark);
    }

    public void addSubFolder(BookMarkCollection subFolder) {
        this.subFolders.add(subFolder);
    }

    public List<BookMark> getBookmarks() {
        return bookmarks;
    }

    public List<BookMarkCollection> getSubFolders() {
        return subFolders;
    }

    @Override
    public String toString() {
        return "BookmarkCollection{" +
                "folderTitle='" + folderTitle + '\'' +
                ", bookmarks=" + bookmarks +
                ", subFolders=" + subFolders +
                '}';
    }
}