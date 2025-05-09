package com.cmr.bookmarks.util;

import com.cmr.bookmarks.model.BookMark;
import com.cmr.bookmarks.model.BookMarkCollection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BookMarkWriter {

    public static void writeBookmarks(BookMarkCollection rootCollection, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n");
            writer.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
            writer.write("<TITLE>Bookmarks</TITLE>\n");
            writer.write("<H1>Bookmarks</H1>\n");
            writer.write("<DL><p>\n");
            writeCollection(rootCollection, writer);
            writer.write("</DL><p>\n");
        }
    }

    private static void writeCollection(BookMarkCollection collection, BufferedWriter writer) throws IOException {
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            writer.write("<DT><H3 ADD_DATE=\"" + subFolder.getAddDate() + "\" LAST_MODIFIED=\"" + subFolder.getLastModified() + "\">" + subFolder.getFolderTitle() + "</H3>\n");
            writer.write("<DL><p>\n");
            writeCollection(subFolder, writer);
            writer.write("</DL><p>\n");
        }
        for (BookMark bookmark : collection.getBookmarks()) {
            writer.write("<DT><A HREF=\"" + bookmark.getUrl() + "\" ADD_DATE=\"" + bookmark.getAddDate() + "\" LAST_MODIFIED=\"" + bookmark.getLastModified() + "\">" + bookmark.getTitle() + "</A>\n");
        }
    }

}
