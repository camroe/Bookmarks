package com.cmr.bookmarks.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SystemPropertiesPrinter {
    private static final Logger logger = LoggerFactory.getLogger(SystemPropertiesPrinter.class);

    public static void printSystemProperties() {
        // Get system properties and sort them
        Map<String, String> sortedProperties = new TreeMap<>(System.getProperties().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString()
                )));
        // Print system properties in alphabetical order
        sortedProperties.forEach((key, value) -> logger.info(key + ": " + value));    }


    }
