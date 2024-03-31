package de.walter.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LineReader {
    public static ArrayList<String> readFromFile(String filePath) {
        ArrayList<String> imports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line here, for demonstration we are just printing it
                if (!line.startsWith("//")){
                    if (!(line.trim().length()==0)){
                        imports.add(line);
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        //System.out.println("Number of all Imports: "+ imports.size());
        return imports;
    }
}
