package org.purl.co.interfaces;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

/**
 * This interface represents a CO collection, i.e., either a CO set, bag or list. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COCollection<E> extends Collection<E> {
	/**
	 * The URI for the ontology class Collection.
	 */
	public static final URI Collection = URI.create("http://purl.org/co/Collection");
	
	/**
	 * The URI for the ontology object property element.
	 */
	public static final URI element = URI.create("http://purl.org/co/element");
	
	/**
	 * The URI for the ontology data property size.
	 */
	public static final URI size = URI.create("http://purl.org/co/size");
	
	/**
	 * This method returns all the objects that are part of this collection.
	 * 
	 * @return a set of objects being part of this collection.
	 */
	public Set<E> elements();
	
	/**
	 * This method returns the size of the collection.
	 * 
	 * @return an integer representing the size of the collection
	 */
	public int size();
	
	/**
	 * This method returns the URI identifying this collection.
	 * 
	 * @return an URI identifying this collection.
	 */
	public URI getCOId();
}
