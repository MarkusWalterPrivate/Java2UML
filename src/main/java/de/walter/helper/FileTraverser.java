package de.walter.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static de.walter.helper.LineReader.readFromFile;

public class FileTraverser {
    HashMap<String, ArrayList<String>> importMap = new HashMap<>();

    public HashMap<String, ArrayList<String>> readFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return null;
        }
        System.out.println("Traversing Files");
        return readFilesInDirectoryHelper(directory);
    }

    private HashMap<String, ArrayList<String>> readFilesInDirectoryHelper(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFilesInDirectoryHelper(file);
                } else {
                    if (file.getName().endsWith("java")){
                        try {
                            //System.out.println("Reading File "+ file.getName());
                            ArrayList<String> fileContent = readFromFile(file.getAbsolutePath());
                            ArrayList<String> imports = new ArrayList<>();
                            String packageName = "";
                            for (String line: fileContent) {
                                //System.out.println("Reading Line " + line);
                                if (line.trim().startsWith("package")){
                                    packageName = line.replace("package", "").trim();
                                }
                                if (line.trim().startsWith("import")){
                                    imports.add(line.replace("import", "").replace("static", "").replace(";", "").trim());
                                }
                            }
                            //System.out.println("Package: "+packageName);
                            importMap.put(packageName.replace(";", ".")+file.getName().replace(".java", ""), imports);
                            //System.out.println("read file " + file.getName());
                        } catch (Exception e) {
                            System.err.println("Error reading file: " + file.getAbsolutePath() + " - " + e.getMessage());
                        }
                    }
                }
            }
        }
        //System.out.println("Number of all Files: "+ importMap.size());
        return importMap;
    }


}
