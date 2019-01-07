package com.inifile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * Class for obtaining IniFile-Instances using Factory-Methods.
 * </p>
 * 
 * @author Holger Dörner
 * @version 1.0
 * @see com.inifile.IniFile
 */
public final class IniFiles {
	// private constructor
	private IniFiles() {}
	
	/**
	 * 
	 * @param file a {@code java.lang.String} representing a Filepath
	 * @return an instance of {@code com.inifile.IniFile}
	 * @throws IOException
	 * @see com.inifile.IniFile
	 */
	public static final IniFile load(String file) throws IOException {
		if (file == null) throw new IllegalArgumentException("Argument cannot be null!");
		
		return load(Paths.get(file));
	}
	
	/**
	 * 
	 * @param file a {@code java.io.File} representing a File
	 * @return an instance of {@code com.inifile.IniFile}
	 * @throws IOException
	 * @see com.inifile.IniFile
	 */
	public static final IniFile load(File file) throws IOException {
		if (file == null) throw new IllegalArgumentException("Argument cannot be null!");
		
		return load(file.toPath());
	}

	/**
	 * 
	 * @param file a {@code java.nio.file.Path} representing a File
	 * @return an instance of {@code com.inifile.IniFile}
	 * @throws IOException
	 * @see com.inifile.IniFile
	 */
	public static final IniFile load(Path file) throws IOException {
		if (file == null) throw new IllegalArgumentException("Argument cannot be null!");
		
		Map<String, Map<String, String>> tmpSettings = new HashMap<>();
		List<String> sectionNames = new ArrayList<>();
		List<List<String[]>> entryPairs = new ArrayList<>();
		
		boolean isMultilineValue = false;
		String[] tmpEntryPair = new String[2];
		
		for (String s : Files.readAllLines(file)) {
			s = s.trim(); // remove leading and trailing white-spaces first, makes processing easier
			
			if (s.isBlank() || s.startsWith(";") || s.startsWith("#")) {} // just skip comments and empty lines
			else if (s.startsWith("[") & s.endsWith("]")) {
				String tmpSectionName = s.substring(1, s.length()-1).trim();
				
				if (sectionNames.contains(tmpSectionName))
					throw new DuplicateEntryException("Dublicate of Section '" + tmpSectionName + "'");
				else {
					sectionNames.add(tmpSectionName);
					entryPairs.add(new ArrayList<>());
				}
			}
			else {
				if (! isMultilineValue) {					
					if (s.contains("=")) {
						Arrays.fill(tmpEntryPair, ""); // make sure array is empty before re-using it
						
						int separatorIndex = s.indexOf("=");
						tmpEntryPair[0] = s.substring(0, separatorIndex);
						tmpEntryPair[1] = s.substring(separatorIndex+1);
						
						if (tmpEntryPair[1].endsWith("\\")) {
							tmpEntryPair[1] = tmpEntryPair[1].substring(0, tmpEntryPair[1].length()-1);
							isMultilineValue = true;
						}
					}
				
					if (!isMultilineValue &&
							(entryPairs.get(sectionNames.size()-1).stream().filter(e -> e[0].equals(tmpEntryPair[0])).count() > 0))
						throw new DuplicateEntryException("Dublicate of Key '" + tmpEntryPair[0] + "'");
					else if (! isMultilineValue)
						entryPairs.get(sectionNames.size()-1).add(tmpEntryPair.clone());
				}
				else {
					if (s.endsWith("\\")) {
						tmpEntryPair[1] = tmpEntryPair[1] + s.substring(0, s.length()-1);
					}
					else {
						tmpEntryPair[1] = tmpEntryPair[1] + s;
						
						if (entryPairs.get(sectionNames.size()-1).stream().filter(e -> e[0].equals(tmpEntryPair[0])).count() > 0)
							throw new DuplicateEntryException("Dublicate of Key '" + tmpEntryPair[0] + "'");
						else
							entryPairs.get(sectionNames.size()-1).add(tmpEntryPair.clone());
						
						isMultilineValue = false;
					}
				}
			}
		}
		
		for (int i = 0; i < sectionNames.size(); i++) {
			tmpSettings.put(sectionNames.get(i), entryPairs.get(i).stream()
					.collect(Collectors.toMap(e -> {
						if((e[0].trim().startsWith("\"") & e[0].trim().endsWith("\"")) ||
								(e[0].trim().startsWith("'") & e[0].trim().endsWith("'")))
							return e[0].trim().substring(1, e[0].length()-2);
						else						
							return e[0].trim();
						}, e -> {
							if((e[1].trim().startsWith("\"") & e[1].trim().endsWith("\"")) ||
									(e[1].trim().startsWith("'") & e[1].trim().endsWith("'")))
								return e[1].trim().substring(1, e[1].length()-2);
							else						
								return e[1].trim();
						})));
		}
		
		return getNewIniFileInstance(tmpSettings);
	}
	
	/**
	 * <p>
	 * Factory-Method for generating an anonymous instance of {@code com.inifile.IniFile}
	 * </p>
	 * 
	 * @param _initialSettings_ a {@code Map<String, Map<String, String>>} read from an ini-file
	 * @return a anonymous instance of {@code com.inifile.IniFile}
	 * @see com.inifile.IniFile
	 */
	private static final IniFile getNewIniFileInstance(final Map<String, Map<String, String>> _initialSettings_) {
		return new IniFile() {
			private final Map<String, Map<String, String>> settings = new HashMap<>(_initialSettings_);
			
			@Override
			public final Map<String, Map<String, String>> getContent() {
				return this.settings;
			}

			@Override
			public final Map<String, String> getEntrysBySection(String section) {
				return this.settings.getOrDefault(section, null);
			}

			@Override
			public final String getValue(String section, String key) {
				return this.settings.getOrDefault(section, null).getOrDefault(key, null);
			}
			
			@Override
			public final Set<String> getSectionNames() {
				return this.settings.keySet();
			}
		};
	}
}
