package com.cmr.bookmarks.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileNamesConfig {

    @Value("${com.cmr.bookmarks.model.file.names}")
    private List<String> fileNames;
    @Value("${com.cmr.bookmarks.model.file.basePath}")
    private String filePath;

    public List<String> getFileNames() {
        return fileNames;
    }

    public String getFilePath() {
        return filePath;
    }

    public void printFullFileNames() {
        for (String fileName : fileNames) {
            System.out.println(filePath + fileName);
        }
    }


    @Override
    public String toString() {
        return "FileNamesConfig{" +
                "fileNames=" + fileNames +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
