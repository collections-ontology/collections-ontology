package org.purl.co.classes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COItem;
import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COListItem;

/**
 * This class is the standard implementation of a {@code COList} as an {@code ArrayList}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
@SuppressWarnings({ "serial" })
public class StandardCOList<E> extends ArrayList<E> implements COList<E> {

	private StandardCOBag<E> bag = null;
	
	/**
	 * The ordered list of items contained by the list and used in the class
	 * {@code StandardCOListItem}.
	 */
	protected List<COListItem<E>> items = new ArrayList<COListItem<E>>();
	
	/**
	 * It constructs a new CO list identifiable through an URI.
	 * 
	 * @param id the URI identifying the list.
	 * @param env the environment that created this list. 
	 */
	protected StandardCOList(URI id, COEnvironment<E> env) {
		bag = new StandardCOBag<E>(id, env);
	}
	
	@Override
	public void clear() {
		bag.clear();
		items.clear();
	}
	
	@Override
	public Set<COItem<E>> items() {
		return bag.items();
	}

	@Override
	public Set<COItem<E>> items(E o) {
		return bag.items(o);
	}

	@Override
	public boolean add(URI coitemuri, int index, E o) {
		boolean result = false;
		
		StandardCOListItem<E> item = new StandardCOListItem<E>(coitemuri, o, this);
		
		if (!items.contains(item)) {
			items.add(item);
			super.add(index, o);
			result = bag.add(coitemuri, o);
		}
		
		return result;
	}
	
	@Override
	public void add(int index, E o) {
		add(null, index, o);
	}

	@Override
	public boolean add(E o) {
		return add(null, size(), o);
	}

	@Override
	public boolean addAll(Collection<? extends E> coll) {
		boolean result = false;
		
		for (E o : coll) {
			result |= add(null, size(), o);
		}
		
		return result;
	}
	
	@Override
	public boolean add(URI coitemuri, E o) {
		return add(coitemuri, size(), o);
	}

	@Override
	public boolean remove(COItem<E> item) {
		bag.remove(item);
		return items.remove(item);
	}
	
	@Override
	public E remove(int index) {
		COListItem<E> item = items.remove(index);
		remove(item);
		return item.itemContent();
	}
	
	@Override
	public boolean remove(Object o) {
		remove(indexOf(o));
		return true;
	}
	
	@Override
	public boolean removeAll(Collection<?> coll) {
		boolean result = false;
		
		for (Object o : coll) {
			result |= remove(o);
		}
		
		return result;
	}
	
	@Override
	public boolean retainAll(Collection<?> coll) {
		ArrayList<Object> copy = new ArrayList<Object>(coll);
		for (Object o : coll) {
			if (copy.contains(o)) {
				copy.remove(o);
				remove(o);
			}
		}
		
		return bag.retainAll(coll);
	}

	@Override
	public Set<E> elements() {
		return bag.elements();
	}

	@Override
	public URI getCOId() {
		return bag.getCOId();
	}

	@Override
	public COListItem<E> firstItem() {
		return (items.isEmpty() ? null : items.get(0));
	}

	@Override
	public COListItem<E> lastItem() {
		return (items.isEmpty() ? null : items.get(size() - 1));
	}

	@Override
	public Set<URI> itemURIs() {
		return bag.itemURIs();
	}

}
