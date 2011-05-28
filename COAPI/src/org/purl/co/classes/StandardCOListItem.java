package org.purl.co.classes;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COListItem;

/**
 * This class is the standard implementation of a {@code COListItem<E>}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public class StandardCOListItem<E> extends StandardCOItem<E> implements COListItem<E> {

	private StandardCOList<E> list = null;
	
	/**
	 * It constructs a new CO list item identifiable through an URI.
	 * 
	 * @param id the URI identifying the list item.
	 * @param o the object contained by the list item.
	 * @param list the list that created this list item.
	 */
	protected StandardCOListItem(URI id, E o, StandardCOList<E> list) {
		super(id, o, list);
		this.list = list;
	}

	
	@Override
	public COListItem<E> nextItem() {
		int index = list.items.indexOf(this);
		return (COListItem<E>) (index < list.size() - 1 ? list.items.get(index + 1) : null);
	}

	
	@Override
	public COListItem<E> previousItem() {
		int index = list.items.indexOf(this);
		return (COListItem<E>) (index > 0 ? list.items.get(index - 1) : null);
	}

	
	@Override
	public Set<COListItem<E>> followedBy() {
		int size = list.items.size();
		return new HashSet<COListItem<E>>((Collection<? extends COListItem<E>>) list.items.subList(index(), size));
	}

	
	@Override
	public Set<COListItem<E>> precededBy() {
		return new HashSet<COListItem<E>>((Collection<? extends COListItem<E>>) list.items.subList(0, index() - 1));
	}

	@Override
	public int index() {
		return list.items.indexOf(this) + 1;
	}


	@Override
	public COList<E> firstItemOf() {
		COListItem<E> first = list.firstItem();
		return (first.equals(this) ? list : null);
	}


	@Override
	public COList<E> lastItemOf() {
		COListItem<E> last = list.lastItem();
		return (last.equals(this) ? list : null);
	}

}
