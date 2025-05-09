package com.cmr.bookmarks.model;

import java.util.*;
import java.util.stream.Collectors;

public class BookMarkCollection {
    private String folderTitle;
    private long addDate;
    private long lastModified;
    private List<BookMark> bookmarks;
    private List<BookMarkCollection> subFolders;

    public BookMarkCollection(String folderTitle, long addDate, long lastModified) {
        this.folderTitle = folderTitle;
        this.addDate = addDate;
        this.lastModified = lastModified;
        this.bookmarks = new ArrayList<>();
        this.subFolders = new ArrayList<>();
    }

    public String getFolderTitle() {
        return folderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
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
    // Method to recursively get the number of folders in this collection
    public int getNumberOfFolders() {
        int count = 1; // Count this folder
        for (BookMarkCollection subFolder : subFolders) {
            count += subFolder.getNumberOfFolders(); // Add the count of subfolders
        }
        return count;
    }

    //Method to recursively get the number of bookmarks in this collection
    public int getNumberOfBookmarks() {
        int count = bookmarks.size(); // Count bookmarks in this folder
        for (BookMarkCollection subFolder : subFolders) {
            count += subFolder.getNumberOfBookmarks(); // Add the count of bookmarks in subfolders
        }
        return count;
    }

    public void setBookmarks(List<BookMark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public void setSubFolders(List<BookMarkCollection> subFolders) {
        this.subFolders = subFolders;
    }
    @Override
    public String toString() {
        return "BookMarkCollection{" +
                "folderTitle='" + folderTitle + '\'' +
                ", addDate=" + addDate +
                ", lastModified=" + lastModified +
                ", bookmarks=" + bookmarks +
                ", subFolders=" + subFolders +
                '}';
    }

    // Method to merge two BookMarkCollection instances
    public void mergeWith(BookMarkCollection other, boolean removeDuplicateBookmarks) {
        // 1. Merge Bookmarks
        if (removeDuplicateBookmarks) {
            // Use a Set to track URLs and avoid duplicates
            List<String> existingUrls = this.bookmarks.stream().map(BookMark::getUrl).collect(Collectors.toList());

            for (BookMark bookmark : other.getBookmarks()) {
                if (!existingUrls.contains(bookmark.getUrl())) {
                    this.addBookmark(bookmark);
                    existingUrls.add(bookmark.getUrl());
                }
            }
        } else {
            // Simply add all bookmarks from the other collection
            this.bookmarks.addAll(other.getBookmarks());
        }

        // 2. Merge Subfolders
        // Use a map to group subfolders by name
        Map<String, BookMarkCollection> subfolderMap = new HashMap<>();

        // Add existing subfolders to the map
        for (BookMarkCollection subFolder : this.getSubFolders()) {
            subfolderMap.put(subFolder.getFolderTitle(), subFolder);
        }

        // Iterate through the other collection's subfolders
        for (BookMarkCollection otherSubFolder : other.getSubFolders()) {
            String folderTitle = otherSubFolder.getFolderTitle();

            if (subfolderMap.containsKey(folderTitle)) {
                // Subfolder with the same name exists, so merge them recursively
                BookMarkCollection existingSubFolder = subfolderMap.get(folderTitle);
                existingSubFolder.mergeWith(otherSubFolder, removeDuplicateBookmarks);
            } else {
                // Subfolder with this name doesn't exist, so add it to the current collection
                this.addSubFolder(otherSubFolder);
                subfolderMap.put(folderTitle, otherSubFolder); // Add to the map
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookMarkCollection that = (BookMarkCollection) o;
        return addDate == that.addDate && lastModified == that.lastModified && Objects.equals(folderTitle, that.folderTitle) && Objects.equals(bookmarks, that.bookmarks) && Objects.equals(subFolders, that.subFolders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folderTitle, addDate, lastModified, bookmarks, subFolders);
    }
}