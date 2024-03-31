package de.walter;


import de.walter.filters.ClassesFilter;
import de.walter.helper.FileTraverser;
import de.walter.filters.ImportFilter;
import de.walter.helper.OutputWriter;
import de.walter.helper.UMLSyntaxGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileTraverser fileReader = new FileTraverser();
        String directoryPath = "Input//path//here";
        HashMap<String, ArrayList<String>> importMap = fileReader.readFilesInDirectory(directoryPath);
        System.out.println( "ImportMap size: " + importMap.size());
        ImportFilter importFilter = new ImportFilter();
        ClassesFilter classesFilter = new ClassesFilter();
        importMap = importFilter.filter(importMap);
        System.out.println("ImportMap after import Filter: "+importMap.size());
        importMap = classesFilter.filter(importMap);
        System.out.println("ImportMap after class filter: " + importMap.size());
        UMLSyntaxGenerator umlSyntaxGenerator = new UMLSyntaxGenerator();
        List<String> imports = umlSyntaxGenerator.generatePackageDeclarations(importMap);
        imports.addAll(umlSyntaxGenerator.generateImportArrows(importMap));
        OutputWriter.writeArrayListToFile(imports);

    }
}