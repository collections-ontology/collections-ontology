package org.purl.co.interfaces;

import java.net.URI;
import java.util.Set;

import org.purl.co.exceptions.ExistingCOEntityException;

/**
 * This interface represents a container for CO collection. Through it, it is possible to load, store, create
 * new collections. All the collections must be created by using this class only, that implements the
 * <q>Factory Method Pattern</q> (a good introduction to this pattern can be found in a 
 * <a href="http://en.wikipedia.org/wiki/Factory_method_pattern">Wikipedia article</a>).
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COEnvironment<E> {
	/**
	 * The URI for the ontology object property elementOf.
	 */
	public static final URI elementOf = URI.create("http://purl.org/co/elementOf");
	
	/**
	 * The URI for the ontology obejct property itemContent.
	 */
	public static final URI itemContentOf = URI.create("http://purl.org/co/itemContentOf");
	
	/**
	 * This method creates a new CO list.
	 *  
	 * @param id the URI identifying to the new CO list or null if it is not associated to any identifier.
	 * @return a new CO list.
	 * @throws ExistingCOEntityException if the URI of the new collection has been previously used for another
	 * entity handled by this environment.
	 */
	public COList<E> createCOList(URI id) throws ExistingCOEntityException;
	
	/**
	 * This method creates a new CO list.
	 *  
	 * @return a new CO list.
	 */
	public COList<E> createCOList();
	
	/**
	 * This method creates a new CO bag.
	 *  
	 * @param id the URI identifying to the new CO bag or null if it is not associated to any identifier.
	 * @return a new CO bag.
	 * 
	 * @throws ExistingCOEntityException if the URI of the new collection has been previously used for another
	 * entity handled by this environment.
	 */
	public COBag<E> createCOBag(URI id) throws ExistingCOEntityException;
	
	/**
	 * This method creates a new CO bag.
	 *  
	 * @return a new CO bag.
	 */
	public COBag<E> createCOBag();
	
	/**
	 * This method creates a new CO set.
	 *  
	 * @param id the URI identifying to the new CO set or null if it is not associated to any identifier.
	 * @return a new CO set.
	 * 
	 * @throws ExistingCOEntityException if the URI of the new collection has been previously used for another
	 * entity handled by this environment.
	 */
	public COSet<E> createCOSet(URI id) throws ExistingCOEntityException;
	
	/**
	 * This method creates a new CO set.
	 *  
	 * @return a new CO set.
	 */
	public COSet<E> createCOSet();
	
	/**
	 * This method gets the collection associated to an input id, if it is contained in the environment.
	 * 
	 * @param id the URI identifying a collection.
	 * @return a COCollection object or 'null' if there not exist in the CO environment a collection with
	 * the input id.
	 */
	public COCollection<E> getCollection(URI id);
	
	/**
	 * This method returns all the collections within the environment.
	 * @return a set of all the collections in the environment.
	 */
	public Set<COCollection<E>> getCollections();
	
	/**
	 * This method checks whether the input id is used for a particular CO entity (CO collection or item)
	 * within this environment.
	 * 
	 * @param id the URI to check.
	 * @return true if the URI is used for identifying a CO entity, false otherwise.
	 */
	public boolean isCOEntityInEnvironment(URI id);
	
	/**
	 * This method removes the collection specified as input from the environment.
	 * 
	 * @param coll the collection to be removed.
	 * @return true if the collection has been removed, false otherwise (e.g., the collection is not
	 * part of the environment).
	 */
	public boolean removeCollection(COCollection<E> coll);
	
	/**
	 * This method returns all the CO collections an object is part of.
	 * 
	 * @param o the object that is contained by some CO collections.
	 * @return a set of CO collections containing the object. 
	 */
	public Set<COCollection<E>> elementOf(E o);
	
	/**
	 * This method returns all the CO items an object is referred by.
	 * 
	 * @param o the object that is referred by some CO items.
	 * @return a set of CO items dereferencing the object. 
	 */
	public Set<COItem<E>> itemContentOf(E o);
}
