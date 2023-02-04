package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File directory = new File("../basejava/src");
        System.out.println(directory.getAbsolutePath());
        System.out.println(directory.getCanonicalPath());
        System.out.println(directory.getName() + (directory.isDirectory() ? " is directory" : " is not directory"));
        System.out.println(directory.getParent() + " is parent directory for " + directory.getName() + "\n");

        getDirectory(directory, "");
    }

    public static void getDirectory(File directory, String offset) {
        System.out.println(offset + "Dir: " + directory.getName());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getDirectory(file, offset + "  ");
                } else {
                    System.out.println(offset + "  -> File: " + file.getName());
                }
            }
        }
    }
}

