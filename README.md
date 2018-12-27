# IniFile

### Table of Contents

1. [Description](#description)
2. [Installation](#requirements)
3. [Usage](#usage)

## Description

Small Java-Library for processing ini-Files.

The Factory `IniFiles` for parsing ini-Files and obtaining Objects of type `IniFile`, witch is an Interface.

## Requirements

**Minimum Java-Version:** *1.8* (in fact, it is developed using OpenJDK-11)

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
Leading `"` and `'` are deleted for Kays and Values.

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
"key_3" = 'value 3'

[Section 3]
another key with spaces=value|With'Stupid|Separators 
```
