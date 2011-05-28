package org.purl.co.interfaces;

import java.net.URI;

/**
 * This interface represents a CO item, that is a particular object used in the
 * CO ontology for allowing repetition of elements within a bag or list. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COItem<E> {
	/**
	 * The URI for the ontology class Item.
	 */
	public static final URI Item = URI.create("http://purl.org/co/Item");
	
	/**
	 * The URI for the ontology object property itemOf.
	 */
	public static final URI itemOf = URI.create("http://purl.org/co/itemOf");
	
	/**
	 * The URI for the ontology object property itemContent.
	 */
	public static final URI itemContent = URI.create("http://purl.org/co/itemContent");
	
	/**
	 * This method returns the object dereferenced by the CO item.
	 * 
	 * @return an object referred by the item.
	 */
	public E itemContent();
	
	/**
	 * This method returns the CO bag that refers to this item.
	 * 
	 * @return the CO bag referring to this item.
	 */
	public COBag<E> itemOf();
	
	/**
	 * This method returns the URI identifying this item.
	 * 
	 * @return an URI identifying this item.
	 */
	public URI getCOId();
}
