package com.cmr.bookmarks.model;

import com.cmr.bookmarks.ConsoleColors;

import static java.lang.System.out;

public final class BookmarkPrinter {

    private BookmarkPrinter() {
    }

    public static void printTree(BookMarkCollection collection, int level) {
        //Start on a new line
        out.println();
        // Print the folder name with indentation
        printIndent(level);
        out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + collection.getFolderTitle() + ConsoleColors.RESET);

        // Print the bookmarks with indentation
        for (BookMark bookmark : collection.getBookmarks()) {
            printIndent(level + 1);
            out.println(ConsoleColors.BLUE + bookmark.getTitle() + ConsoleColors.RESET);
        }

        // Recursively print the subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            printTree(subFolder, level + 1);
        }
    }


    public static void printFolders(BookMarkCollection collection, int level) {
        // Print the folder name with indentation
        printIndent(level);
        out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + collection.getFolderTitle() + ConsoleColors.RESET);

        // Recursively print the subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            printFolders(subFolder, level + 1);
        }
    }

    private static void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            out.print("    ");
        }
    }
}
