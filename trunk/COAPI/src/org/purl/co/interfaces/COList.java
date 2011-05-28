package org.purl.co.interfaces;

import java.net.URI;

/**
 * This interface represents a CO list as a specialisation of a CO bag,
 * i.e., a CO collection with order and repetition. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COList<E> extends COBag<E> {
	/**
	 * The URI for the ontology class List.
	 */
	public static final URI List = URI.create("http://purl.org/co/List");
	
	/**
	 * The URI for the ontology objectPropery firstItem.
	 */
	public static final URI firstItem = URI.create("http://purl.org/co/firstItem");
	
	/**
	 * The URI for the ontology object property lastItem.
	 */
	public static final URI lastItem = URI.create("http://purl.org/co/lastItem");
	
	/**
	 * This method returns the first item of the list.
	 * 
	 * @return a CO list item dereferencing the first object of the list.
	 */
	public COListItem<E> firstItem();
	
	/**
	 * This method returns the last item of the list.
	 * 
	 * @return a CO list item dereferencing the last object of the list.
	 */
	public COListItem<E> lastItem();
	
	/**
	 * This method adds a new object to the list, that will be dereferenced 
	 * through a new CO list item created starting
	 * from the URI specified as input.
	 * 
	 * @param coitemuri the URI of the list item dereferencing the object of the list.
	 * @param index the position in which adding the list item.
	 * @param o the object to add.
	 * @return true if the object it is correctly added, false otherwise.
	 */
	public boolean add(URI coitemuri, int index, E o);
}
