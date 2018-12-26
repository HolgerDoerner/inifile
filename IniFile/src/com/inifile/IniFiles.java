package com.inifile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	 * @param file a {@code java.lang.String} representing a File
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
		
		List<String> lines = Files.readAllLines(file);
		
		for (String s : lines) {
			if (s.isBlank() || s.startsWith(";") || s.startsWith("#")) {}
			else if (s.startsWith("[") & s.endsWith("]")) {
				String tmp = s.substring(1, s.length()-1).trim();
				
				if (sectionNames.contains(tmp)) throw new DuplicateEntryException("Dublicate of Section '" + tmp + "'");
				
				sectionNames.add(tmp);
				entryPairs.add(sectionNames.size()-1, new ArrayList<>());
			}
			else {
				String[] tmp = s.split("=");
				
				if (entryPairs.get(sectionNames.size()-1).stream().filter(e -> e[0].equals(tmp[0])).count() > 0)
					throw new DuplicateEntryException("Dublicate of Key '" + tmp[0] + "'");
				
				entryPairs.get(sectionNames.size()-1).add(tmp);
			}
		}
		
		for (int i = 0; i < sectionNames.size(); i++) {
			tmpSettings.put(sectionNames.get(i), entryPairs.get(i).stream()
					.collect(Collectors.toMap(e -> e[0].replaceAll("'", "").replaceAll("\"", "").trim(),
							e -> e[1].replaceAll("'", "").replaceAll("\"", "").trim())));
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
