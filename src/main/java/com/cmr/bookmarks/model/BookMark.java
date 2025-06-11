package com.cmr.bookmarks.model;

import java.util.Objects;

public class BookMark {
    private String title;
    private String url;
    private String folder;
    private String icon;
    private long addDate;
    private long lastModified;
    private boolean isValid = false;
    private boolean isDuplicate = false;

    public BookMark(String title, String url, String folder, String icon, long addDate, long lastModified) {
        this.title = title;
        this.url = url;
        this.folder = folder;
        this.icon = icon;
        this.addDate = addDate;
        this.lastModified = lastModified;
    }

    public BookMark(String title, String url, String icon, String addDate, String lastModified) {
        this.title = title;
        this.url = url;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
        return Objects.equals(title, bookMark.title) && Objects.equals(url, bookMark.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}