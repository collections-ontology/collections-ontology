package org.purl.co.interfaces;

import java.net.URI;
import java.util.Set;

/**
 * This interface represents a CO list item, that is a particular CO item used in the
 * CO ontology for allowing order and repetition of elements within a list. 
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public interface COListItem<E> extends COItem<E> {
	/**
	 * The URI for the ontology class ListItem.
	 */
	public static final URI ListItem = URI.create("http://purl.org/co/ListItem");
	
	/**
	 * The URI for the ontology object property nextItem.
	 */
	public static final URI nextItem = URI.create("http://purl.org/co/nextItem");
	
	/**
	 * The URI for the ontology object property previousItem.
	 */
	public static final URI previousItem = URI.create("http://purl.org/co/previousItem");
	
	/**
	 * The URI for the ontology object property followedBy.
	 */
	public static final URI followedBy = URI.create("http://purl.org/co/followedBy");
	
	/**
	 * The URI for the ontology object property precededBy.
	 */
	public static final URI precededBy = URI.create("http://purl.org/co/precededBy");
	
	/**
	 * The URI for the ontology object property firstItemOf.
	 */
	public static final URI firstItemOf = URI.create("http://purl.org/co/firstItemOf");
	
	/**
	 * The URI for the ontology object property lastItemOf.
	 */
	public static final URI lastItemOf = URI.create("http://purl.org/co/lastItemOf");
	
	/**
	 * The URI for the ontology data property index.
	 */
	public static final URI index = URI.create("http://purl.org/co/index");
	
	/**
	 * This method returns the list item directly following this one.
	 * 
	 * @return the direct-following list items or null if it does not exist.
	 */
	public COListItem<E> nextItem();
	
	/**
	 * This method returns the list item directly preceding this one.
	 * 
	 * @return the direct-preceding list items or null if it does not exist.
	 */
	public COListItem<E> previousItem();
	
	/**
	 * This method returns all the list items that follow this one.
	 * 
	 * @return a set of list items that follow this one.
	 */
	public Set<COListItem<E>> followedBy();
	
	/**
	 * This method returns all the list items that precede this one.
	 * 
	 * @return a set of list items that precede this one.
	 */
	public Set<COListItem<E>> precededBy();
	
	/**
	 * This method returns the list in which this item list is the first item.
	 * 
	 * @return the list having this item as the first one.
	 */
	public COList<E> firstItemOf();
	
	/**
	 * This method returns the list in which this item list is the last item.
	 * 
	 * @return the list having this item as the last one.
	 */
	public COList<E> lastItemOf();
	
	/**
	 * This method returns the index of this list item within the list that contains it.
	 * 
	 * @return an integer identifying the position of the list item within the list.
	 */
	public int index();
	
}
