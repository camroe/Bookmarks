package com.cmr.bookmarks.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileNamesConfig {

    @Value("${com.cmr.bookmarks.model.file.names}")
    private List<String> fileNames;
    @Value("${com.cmr.bookmarks.model.file.basePath}")
    private String filePath;
    @Value("${com.cmr.bookmarks.model.file.output:defaultOutputFile.html}")
    private String outputfile;

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

    public boolean isMultipleFiles() {
        return fileNames.size() > 1;
    }

    @Override
    public String toString() {
        return "FileNamesConfig{" + "fileNames=" + fileNames + ", filePath='" + filePath + '\'' + '}';
    }

    public String getOutputFile() {
        return filePath + outputfile;
    }
}
