package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalFile());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//
//        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if(list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//        try (FileInputStream fis = new FileInputStream(filePath)){
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        File directory = new File("..\\basejava\\src");
        getDirectory(directory);
    }

    private static void getDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File name: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Directory name: " + file.getName());
                    getDirectory(file);
                }
            }
        }
    }
}
