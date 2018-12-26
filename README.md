# IniFile

*Small Java-Library for processing ini-Files.*

## Description

The Factory `IniFiles` for parsing ini-Files and obtaining Objects of type `IniFile`, witch is an Interface.

## Installation

Clone the Repository and build from Source.

**OR**

Download JAR-File:

Filename | Version | Date | Binary | Source | Javadoc | SHA-1
-------- | ------- | ---- | ------ | ------ | ------- | -----
<a href="data:application/octet-stream,DATA" download="IniFile/jar/IniFile-1.0-FULL.jar">IniFile-1.0-FULL.jar</a> | 1.0 | 2018-12-26 | *Yes* | *Yes* | *Yes* | 98303ad4fe1066b0cf8a03b542bbd87f49fc07ae

**Requires:** *Java 1.8+* (in fact, it is developed using JDK-11)

**External Dependencies:** *None* 

## Usage

``` Java
try {
	IniFile ini = IniFiles.load("/path/to/file.ini");
}
catch (IOException e) {
	// handling of Exception...
}
```

The Factory-Class `IniFiles` defines 1 static method, witch is overloaded multiple times:

`public IniFile load(Path path) throws IOException { ... }`

`public IniFile load(File file) throws IOException { ... }`

`public IniFile load(String file) throws IOException { ... }`

Currently the Library is able to parse simple ini-Files. If it detects duplicated Section-Headers or duplicated Keys inside of the same Section, `IniFiles` 
will throw an *unchecked* `DuplicateEntryException`.

Comments starting with `;` or `#` are ignored as well as empty lines.\
The Characters `"`, `'` and `|` are replaced with empty Characters (`""`).

Here is a sample *test.ini* as it is used by my *JUnit*-Tests:

``` ini
; first comment
[Main]
One = 1
Two = 2
Three = 3

# second comment...
# ...on two lines
[Section_2]
key = value
key_2 = "value 2"
key_3 = 'value 3'
```
