package com.cmr.scratch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class ExampleNextElementSibling {
    public static void main(String[] args) {
        try {
            File input = new File("src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html");
            Document doc = Jsoup.parse(input, "UTF-8");
            Element firstElement = doc.selectFirst("DT");
            Element nextSibling = firstElement.nextElementSibling();
            if (nextSibling != null) {
                System.out.println("Next sibling tag: " + nextSibling.tagName() + " text: " + nextSibling.text());
            } else {
                System.out.println("There is no next sibling element.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}