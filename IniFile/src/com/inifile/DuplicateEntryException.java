package com.inifile;

/**
 * Indicates that an Dulicate of a Section or a Key inside of a Section
 * was found in the File.
 * 
 * @author Holger Dörner
 * @version 1.0
 * @see java.lang.RuntimeException *
 */
public class DuplicateEntryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DuplicateEntryException() { super(); }	
	public DuplicateEntryException(String message) { super(message); }
}
