/**
 * 
 */
package com.inifile.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.inifile.IniFile;
import com.inifile.IniFiles;

/**
 * <p>
 * JUnit-Tests for {@code com.inifile.IniFile}
 * </p>
 * 
 * @author Holger Dörner
 * @see com.inifile.IniFile
 */
@TestInstance(Lifecycle.PER_CLASS)
class IniFileTest {
	IniFile ini;
	
	@BeforeAll
	void init() throws IOException {
		ini = IniFiles.load(Paths.get("test.ini"));
	}
	
	/**
	 * Test method for {@link com.inifile.IniFile#getContent()}.
	 */
	@Test
	void testGetContent() {
		assertTrue(ini.getContent() != null & ini.getContent() instanceof Map);
	}

	/**
	 * Test method for {@link com.inifile.IniFile#getEntrysBySection(java.lang.String)}.
	 */
	@Test
	void testGetEntrysBySection() {
		assertTrue(ini.getEntrysBySection("Main") != null & ini.getEntrysBySection("Main").containsKey("Three"));
	}

	/**
	 * Test method for {@link com.inifile.IniFile#getValue(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetValue() {
		assertEquals("2", ini.getValue("Main", "Two"));
		assertEquals("value 3", ini.getValue("Section_2", "key_3"));
	}

	/**
	 * Test method for {@link com.inifile.IniFile#getSectionNames()}.
	 */
	@Test
	void testGetSectionNames() {
		assertTrue(ini.getSectionNames().contains("Main"));
		assertTrue(ini.getSectionNames().contains("Section_2"));
	}

}
