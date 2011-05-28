package org.purl.co.interfaces;

import java.io.File;

/**
 * This interface represents a writer for CO representation. It must be opportunely implemented
 * for creating CO linearisations in different formats (RDF/XML, Turtle, etc.) 
 * starting from their Java representations.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COWriter<E> {
	/**
	 * This method create a CO linearisation in a specific format starting from its
	 * Java representation.
	 * 
	 * @param env the {@code COEnvironment} to be linearised.
	 * @return the string in a particular format as linearisation of the Java representation specified as input.
	 */
	public String getStringRepresentation(COEnvironment<E> env);
	
	/**
	 * This method create a CO linearisation in a specific format starting from its
	 * Java representation.
	 * 
	 * @param env the {@code COEnvironment} to be linearised.
	 * @param f the file where storing the linearisation of the Java representation specified as input.
	 */
	public void store(COEnvironment<E> env, File f);
}
