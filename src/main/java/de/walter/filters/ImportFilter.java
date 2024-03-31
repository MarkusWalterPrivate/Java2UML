package de.walter.filters;

import de.walter.helper.LineReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImportFilter {
    ArrayList<String> packagesToIgnore;

    public ImportFilter (){
        this.packagesToIgnore = readLinesFromFile("importsToIgnore.txt");
    }

    public HashMap<String, ArrayList<String>> filter (HashMap<String, ArrayList<String>> imports){
        HashMap<String, ArrayList<String>> output = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : imports.entrySet()) {
            boolean entryWasMathced = false;
            for (String classToIgnore : packagesToIgnore) {
                //System.out.println("File: "+ entry.getKey());

                //in case of wildcard:
                if (classToIgnore.endsWith("*")) {
                    //if entry is not to be removed, check import statements in entry and add it to output
                    if (entry.getKey().startsWith(classToIgnore.replace("*", ""))) {
                        entryWasMathced = true;
                        //System.out.println("Removing Entry: "+ entry.getKey());
                    }
                } else if (entry.getKey().equals(classToIgnore)) {
                    entryWasMathced = true;
                    //System.out.println("Removing Entry: "+ entry.getKey());
                }
            }
            if (!entryWasMathced){
                output.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, ArrayList<String>> entry: output.entrySet()) {
            ArrayList<String> filteredImports = new ArrayList<>();
            for (String importedClass : entry.getValue()) {
                boolean entryWasMatched = false;
                for (String classToIgnore : packagesToIgnore) {
                    if (classToIgnore.endsWith("*")){
                        if (importedClass.startsWith(classToIgnore.replace("*",""))){
                            entryWasMatched = true;
                            //System.out.println("Removing: "+ importedClass);
                        }
                    }else if (importedClass.equals(classToIgnore)){
                        entryWasMatched = true;
                        //System.out.println("Removing: "+importedClass);
                    }else {

                    }
                }
                if (!entryWasMatched){
                    filteredImports.add(importedClass);
                    //System.out.println("Keeping: "+importedClass);
                }
            }
            output.replace(entry.getKey(), entry.getValue(), filteredImports);
        }

        return output;
    }




    private ArrayList<String> readLinesFromFile(String filePath) {
        ArrayList<String> input = LineReader.readFromFile(filePath);
        System.out.println("Packages to ignore:");
        for (String line : input) {
            System.out.println(line);
        }
        return input;
    }


}
