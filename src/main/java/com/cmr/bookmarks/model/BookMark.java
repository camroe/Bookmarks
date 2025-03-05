package com.cmr.bookmarks.model;

public class BookMark {
    private String title;
    private String url;
    private String folder;
    private long addDate;
    private long lastModified;

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
}