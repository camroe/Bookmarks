package com.cmr.bookmarks.model;

import java.util.Objects;

public class BookMark {
    private String title;
    private String url;
    private String folder;
    private long addDate;
    private long lastModified;
    private boolean isValid = false;
    private boolean isDuplicate = false;

    public BookMark(String title, String url, String folder, long addDate, long lastModified) {
        this.title = title;
        this.url = url;
        this.folder = folder;
        this.addDate = addDate;
        this.lastModified = lastModified;
    }

    public BookMark(String title, String url, String addDate, String lastModified) {
        this.title = title;
        this.url = url;
        this.addDate = Long.parseLong(addDate);
        setLastModified(lastModified);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
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

    public void setLastModified(String lastModified) {
        if (lastModified == null || lastModified.isEmpty()) {
            this.lastModified = 0;
            ;
        } else {
            this.lastModified = Long.parseLong(lastModified);
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", folder='" + folder + '\'' +
                ", addDate=" + addDate +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookMark bookMark = (BookMark) o;
        return addDate == bookMark.addDate && lastModified == bookMark.lastModified && isValid == bookMark.isValid && isDuplicate == bookMark.isDuplicate && Objects.equals(title, bookMark.title) && Objects.equals(url, bookMark.url) && Objects.equals(folder, bookMark.folder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, folder, addDate, lastModified, isValid, isDuplicate);
    }
}