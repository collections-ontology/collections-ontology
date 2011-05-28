package org.purl.co.interfaces;

import java.net.URI;
import java.util.Set;

/**
 * This interface represents a CO bag, i.e., a CO collection without order but with repetition. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COBag<E> extends COCollection<E> {
	/**
	 * The URI for the ontology class Bag.
	 */
	public static final URI Bag = URI.create("http://purl.org/co/Bag");
	/**
	 * The URI for the ontology object property item.
	 */
	public static final URI item = URI.create("http://purl.org/co/item");
	
	/**
	 * This method returns all the CO items of a bag.
	 * 
	 * @return a set containing all the items of a bag.
	 */
	public Set<COItem<E>> items();
	
	/**
	 * This method returns the URIs of all the CO items of a bag.
	 * 
	 * @return a set containing all the URIs of CO items.
	 */
	public Set<URI> itemURIs();
	
	/**
	 * This method returns all the CO items in the bag that dereference the object.
	 * 
	 * @param o the object dereferenced by the item.
	 * @return the items dereferencing the input object.
	 */
	public Set<COItem<E>> items(E o);
	
	/**
	 * This method adds a new object to the bag, that will be dereferenced through a new CO item created starting
	 * from the URI specified as input.
	 * 
	 * @param coitemuri the URI of the item dereferencing the object of the bag.
	 * @param o the object to add.
	 * @return true if the object it is correctly added, false otherwise - i.e., {@code coitemuri} exists
	 * within the bag. 
	 */
	public boolean add(URI coitemuri, E o);
	
	/**
	 * This method removes from the bag the CO item indicated and, consequently, decreases by 1 the number
	 * of objects {@code o} contained by the bag.
	 * 
	 * @param item the CO item to be removed.
	 * @return true if the item has been removed, false otherwise.
	 */
	public boolean remove(COItem<E> item);
	
}
