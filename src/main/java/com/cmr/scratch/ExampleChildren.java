package com.cmr.scratch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ExampleChildren {

    public static int count = 0;

    public static void main(String[] args) {
        try {
            File input = new File("src/main/resources/data/bookmarks_2_26_25-Mac-Laptop-Bookmarks-Bar.html");
            Document doc = Jsoup.parse(input, "UTF-8");
            Element rootElement = doc.selectFirst("DL");
            Elements children = rootElement.children();
            for (Element child : children) {
                count++;
                if (count <= 2) {
                    System.out.println("Child tag: " + child.tagName());
//                System.out.println("Child text: " + child.text());

                    System.out.println("Child html: " + child.html());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of children: " + count);
    }
}

