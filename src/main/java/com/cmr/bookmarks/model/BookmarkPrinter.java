package com.cmr.bookmarks.model;

import com.cmr.bookmarks.ConsoleColors;

public class BookmarkPrinter {

    public static void printTree(BookMarkCollection collection, int level) {
        // Print the folder name with indentation
        printIndent(level);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + collection.getFolderTitle() + ConsoleColors.RESET);

        // Print the bookmarks with indentation
        for (BookMark bookmark : collection.getBookmarks()) {
            printIndent(level + 1);
            System.out.println(ConsoleColors.BLUE + bookmark.getTitle() + ConsoleColors.RESET);
        }

        // Recursively print the subfolders
        for (BookMarkCollection subFolder : collection.getSubFolders()) {
            printTree(subFolder, level + 1);
        }
    }

    private static void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }
}
