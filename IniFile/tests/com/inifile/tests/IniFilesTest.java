package com.inifile.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.inifile.IniFile;
import com.inifile.IniFiles;

/**
 * <p>
 * JUnit-Tests for {@code com.inifile.IniFiles}
 * </p>
 * 
 * @author Holger Dörner
 * @see com.inifile.IniFiles
 */
class IniFilesTest {
	/**
	 * Test method for {@link com.inifile.IniFiles#load(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	void testLoadString() {
		IniFile ini = null;
		
		try {
			ini = IniFiles.load("tests/test.ini");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertTrue(ini != null && ini instanceof IniFile);
	}

	/**
	 * Test method for {@link com.inifile.IniFiles#load(java.io.File)}.
	 */
	@Test
	void testLoadFile() {
		IniFile ini = null;
		
		try {
			ini = IniFiles.load(new File("tests/test.ini"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertTrue(ini != null && ini instanceof IniFile);
	}

	/**
	 * Test method for {@link com.inifile.IniFiles#load(java.nio.file.Path)}.
	 */
	@Test
	void testLoadPath() {
		IniFile ini = null;
		
		try {
			ini = IniFiles.load(Paths.get("tests/test.ini"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertTrue(ini != null && ini instanceof IniFile);
	}

}
