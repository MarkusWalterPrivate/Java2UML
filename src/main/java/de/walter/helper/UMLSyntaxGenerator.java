package de.walter.helper;

import java.util.*;

import static de.walter.helper.LineReader.readFromFile;

public class UMLSyntaxGenerator {

    public ArrayList<String> generateImportArrows(HashMap<String, ArrayList<String>> imports){
        ArrayList<String> output = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : imports.entrySet()) {
            //System.out.println("File: "+ entry.getKey());
            for (String importedClass : entry.getValue()) {
                output.add(removeUnwantedSubstrings(entry.getKey())+ " --> " + removeUnwantedSubstrings(importedClass.replace(";","")));
                //System.out.println(importedClass);
            }
        }
        return output;
    }
    public List<String> generatePackageDeclarations(HashMap<String, ArrayList<String>> imports){
        Set<String> packages = new HashSet<>();
        //generating set of unique package declarations
        for (Map.Entry<String, ArrayList<String>> entry : imports.entrySet()) {
            packages.add(removeUnwantedSubstrings(entry.getKey().replace(";", "")));
            for (String importedClass : entry.getValue()) {
                packages.add(removeUnwantedSubstrings(importedClass.replace(";", "")));
            }
        }
        List<String> packageDeclarations = new ArrayList<>();
        packageDeclarations.addAll(writeMixedPackage(packages));

        return packageDeclarations;
    }

    private List<String> writeMixedPackage(Set<String> packages){
        List<String> output = new ArrayList<>();
        Set<String> packageTokens = new HashSet<>();
        //generating list of direct children
        for (String packageName: packages) {
            String[] splitName = packageName.split("\\.");
            if (splitName.length>0){
                packageTokens.add(splitName[0]);
            }
        }
        //now we have a list of all Child packages to work with:
        for (String childPackage: packageTokens) {
            Set<String> childrenWithThatName = new HashSet<>();
            //only add this line if package is not final stop
            boolean isLeaf= false;
            for (String packageName: packages) {
                if (packageName.equals(childPackage)&&!packageName.equals("*")){
                    if (Character.isUpperCase(packageName.charAt(0))){
                        output.add("class "+ packageName + "{");
                        output.add("}");
                    }else {
                        output.add("package "+ packageName + "{");
                        output.add("}");
                    }

                    isLeaf = true;
                }
                else if (packageName.startsWith(childPackage)){
                    childrenWithThatName.add(packageName.replace(childPackage+".", ""));
                }

            }
            if (childPackage.equals("*")){
                output.add("package *{");
                output.add("}");
            }
            else if (!isLeaf){
                output.add("package "+ childPackage + "{");
                output.addAll(writeMixedPackage(childrenWithThatName));
                output.add("}");
            }

        }
        return output;

    }
    private String removeUnwantedSubstrings(String input){
        ArrayList<String> packagePrefixes = LineReader.readFromFile("packagePrefixToIgnore.txt");
        for (String line: packagePrefixes) {
            if (input.startsWith(line)){
                return input.replace(line+".", "");
            }
        }
        return input;
    }
}
