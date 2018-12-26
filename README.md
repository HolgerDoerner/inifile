# IniFile

*Small Java-Library for processing ini-Files.*

## Description

The Factory `IniFiles` for parsing ini-Files and obtaining Objects of type `IniFile`, witch is an Interface.

## Usage

``` Java
try {
	IniFile ini = IniFiles.load("/path/to/file.ini");
}
catch (IOEception e) {
	// handling of Exception...
}
```

The Factory `IniFiles` defines 1 static method, witch is overloaded multiple times:

`public IniFile load(Path path) throws IOException { ... }`

`public IniFile load(File file) throws IOException { ... }`

`public IniFile load(String file) throws IOException { ... }`

## Installation

Clone the Repository and build from Source.\
Binary jar-Files will also be provided in future :smile:

**Requires:** *Java 1.8+* (in fact, it is developed using JDK-11)

**External Dependencies:** *None* 
