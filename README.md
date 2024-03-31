# Java2UML
A simple project to generate PlantUML syntax from .java files. Allows filtering of packages, classes and removal of prefixes, like 'org.example'

## How to use it:
In the main class you have to enter the path in 'directoryPath' of the folder you want to have analysed (generally src or a subfolder).
You have three configuration options, as seen below. Each option allows commenting with // only, empty lines will be ignored.
### importsToIgnore.txt
Here you can define what kind of imputs should be ignored. You have two options:
- wildcard: will exclude all paackages and classes matching the wildcard e.g. java.* -> ignores all packages and subpackages from java
- exact defintion: foo.Bar will ignore only foo.Bar, you will need to enter the full path e.g. org.example.foo.Bar

### packagePrefixToIgnore.txt
Allows you to get rid of prefixes, like org.example. Such prefixes are simply removed from any String.

### classesToIgnore.txt
Allows you toignroe certain classes based on the suffix. You have two options:
- wildcard: Will exclude all classes ending a certain way e.g. *DTO removes all classes endign in DTO, *foo.Bar removes all Bar classes in the foo package.
- exact definition: foo.Bar will ignore only foo.Bar, you will need to enter the full path e.g. org.example.foo.Bar

## Functionality
Will output package and class definitions like this: org.example.foo.Bar and org.example.foo.Ber turn to:
package org{
package example {
package foo {
class Bar{
}
class Ber{
}
}
}
}

Imports will turn to:
packageName.importingClass --> importDefinition
