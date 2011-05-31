package org.purl.co.classes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Set;

import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COItem;

/**
 * This class is the standard implementation of a {@code COBag} as a {@code CuncurrentLinkedQueue}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
@SuppressWarnings("serial")
public class StandardCOBag<E> extends ConcurrentLinkedQueue<E> implements COBag<E> {

	private URI id = null;
	private Map<COItem<E>,E> items = new HashMap<COItem<E>,E>();
	private Map<E,Set<COItem<E>>> objects = new HashMap<E,Set<COItem<E>>>();
	private Set<URI> itemURIs = new HashSet<URI>(); 
	
	/**
	 * It constructs a new CO bag identifiable through an URI.
	 * 
	 * @param id the URI identifying the bag. 
	 * @param env the environment that created this bag.
	 */
	protected StandardCOBag(URI id, COEnvironment<E> env) {
		this.id = id;
	}
	
	@Override
	public Set<E> elements() {
		return new HashSet<E>(this);
	}
	
	@Override
	public URI getURI() {
		return id;
	}

	@Override
	public Set<COItem<E>> items() {
		return new HashSet<COItem<E>>(items.keySet());
	}
	
	@Override
	public boolean add(E o) {
		return add(null, o);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> coll) {
		boolean result = false;
		
		for (E o : coll) {
			result |= add(null, o);
		}
		
		return result;
	}
	
	@Override
	public boolean remove(Object o) {
		return remove(o);		
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
		boolean result = false;
		
		ArrayList<Object> copy = new ArrayList<Object>(coll);
		for (Object o : coll) {
			if (copy.contains(o)) {
				copy.remove(o);
				result |= remove(o);
			}
		}
		
		return result;
	}
	
	@Override
	public void clear() {
		items.clear();
		itemURIs.clear();
		objects.clear();
		super.clear();
	}

	@Override
	public boolean add(URI coitemuri, E o) {
		boolean result = false;
		
		StandardCOItem<E> item = new StandardCOItem<E>(coitemuri, o, this);
		
		if (!items.containsKey(item)) {
			items.put(item, o);
			if (coitemuri != null) {
				itemURIs.add(coitemuri);
			}
			
			Set<COItem<E>> set = objects.get(o);
			if (set == null) {
				set = new HashSet<COItem<E>>();
				objects.put(o, set);
			}
			set.add(item);
			
			result = super.add(o);
		}
		
		return result;
	}

	@Override
	public Set<COItem<E>> items(E o) {
		return (objects.containsKey(o) ? new HashSet<COItem<E>>(objects.get(o)) : new HashSet<COItem<E>>());
	}

	@Override
	public boolean remove(COItem<E> item) {
		boolean result = false;
		
		E o = items.get(item);
		if (o != null) {
			items.remove(item);
			if (item.getCOId() != null) {
				itemURIs.remove(item.getCOId());
			}
			Set<COItem<E>> set = objects.get(o);
			set.remove(item);
			if (set.isEmpty()) {
				objects.remove(o);
			}
			
			result = super.remove(o);
		}
		
		
		return result;
	}
	
	public String toString() {
		return (id == null ? super.toString() : id.toString());
	}

	@Override
	public Set<URI> itemURIs() {
		return new HashSet<URI>(itemURIs);
	}
	
	@Override
	public E poll() {
		E result = peek();
		
		if (result != null) {
			remove(result);
		}
		
		return result;
	}
	
	@Override
	public boolean offer(E obj) {
		return add(obj);
	}
}
