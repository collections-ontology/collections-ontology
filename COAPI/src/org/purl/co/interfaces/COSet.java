package org.purl.co.interfaces;

import java.net.URI;


/**
 * This interface represents a CO set, i.e., a CO collection without order and repetition. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COSet<E> extends COCollection<E> {
	/**
	 * The URI for the ontology class Set.
	 */
	public static final URI Set = URI.create("http://purl.org/co/Set");
}
