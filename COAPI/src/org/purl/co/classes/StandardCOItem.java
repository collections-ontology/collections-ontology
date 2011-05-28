package org.purl.co.classes;

import java.net.URI;

import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COItem;

/**
 * This class is the standard implementation of a {@code COItem<E>}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public class StandardCOItem<E> implements COItem<E> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardCOItem<E> other = (StandardCOItem<E>) obj;
		if (id == null) {
			if (other.id != null || this != obj)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private URI id = null;
	private E contained = null;
	private COBag<E> itemOf = null;
	
	/**
	 * It constructs a new CO item identifiable through an URI.
	 * 
	 * @param id the URI identifying the item.
	 * @param o the object contained by the item.
	 * @param bag the bag that created this item.
	 */
	protected StandardCOItem(URI id, E o, COBag<E> bag) {
		this.id = id;
		contained = o;
		itemOf = bag;
	}
	
	@Override
	public E itemContent() {
		return contained;
	}

	@Override
	public COBag<E> itemOf() {
		return itemOf;
	}

	@Override
	public URI getCOId() {
		return id;
	}

	@Override
	public String toString() {
		return (id == null ? super.toString() : id.toString());
	}
}
