package de.walter.helper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {

    public static void writeArrayListToFile(List<String> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UMLInstructions.txt"))) {
            for (String line : list) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("ArrayList content has been written to the file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing ArrayList content to file: " + e.getMessage());
        }
    }
}
