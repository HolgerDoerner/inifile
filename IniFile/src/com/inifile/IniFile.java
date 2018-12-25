package com.inifile;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Holds the Contents of an .INI-File
 * </p>
 * 
 * @author Holger Dörner
 * @version 1.0
 * @see com.inifile.IniFiles
 *
 */
public interface IniFile {
	/** 
	 * @return the Settings grouped by Sections
	 */
	public abstract Map<String, Map<String, String>> getContent();
	
	/**
	 * 
	 * @param section the Section-Identifier
	 * @return a java.util.Map containing the Entrys for the given Section
	 */
	public abstract Map<String, String> getEntrysBySection(String section);
	
	/**
	 * 
	 * @param section the Section-Identifier
	 * @param key the Key-Identifier
	 * @return a java.lang.String containing the Value matching the Key
	 */
	public abstract String getValue(String section, String key);
	
	/**
	 * 
	 * @return a java.util.Set containing the Section-Identifiers
	 */
	public abstract Set<String> getSectionNames();
}
