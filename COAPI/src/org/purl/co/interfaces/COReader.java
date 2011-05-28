package org.purl.co.interfaces;

import java.io.File;

/**
 * This interface represents a reader for CO representation. It must be opportunely implemented
 * for creating Java representations of CO linearisations in different formats (RDF/XML, Turtle, etc.).
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COReader<E> {
	/**
	 * This method create a Java representation starting from a string containing a CO linearisation in
	 * a specific format.
	 * 
	 * @param content the string containing the CO linearisation.
	 * @return a new {@code COEnvironment} representing the input CO linearisation.
	 */
	public COEnvironment<E> read(String content);
	
	/**
	 * This method create a Java representation starting from a file containing a CO linearisation in
	 * a specific format.
	 * 
	 * @param f the file containing the CO linearisation.
	 * @return a new {@code COEnvironment} representing the input CO linearisation.
	 */
	public COEnvironment<E> read(File f);
}
