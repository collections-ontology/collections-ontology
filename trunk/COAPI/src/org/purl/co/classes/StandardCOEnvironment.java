package org.purl.co.classes;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.purl.co.exceptions.ExistingCOEntityException;
import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COCollection;
import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COItem;
import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COSet;

/**
 * This class is the standard implementation of a @code{COEnvironment<E>}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public class StandardCOEnvironment<E> implements COEnvironment<E> {
	
	private Map<URI, COSet<E>> uriSets = new HashMap<URI, COSet<E>>();
	private Map<URI, COBag<E>> uriBags = new HashMap<URI, COBag<E>>();
	private Map<URI, COList<E>> uriLists = new HashMap<URI, COList<E>>();
	private Set<COSet<E>> anonymousSets = new HashSet<COSet<E>>();
	private Set<COBag<E>> anonymousBags = new HashSet<COBag<E>>();
	private Set<COList<E>> anonymousLists = new HashSet<COList<E>>();
	
	/**
	 * <p>This method allows to use a different kinds of implementation of {@code COList}, rather than
	 * {@code StandardCOList} that is used as default by this class.</p>
	 * <p>This method becomes particularly useful when we want to extend this class keeping the same
	 * behaviour except of using different kind of collection implementations. For lists, this
	 * can be easily implemented by overriding the public creation methods for lists as follows:</p>
	 * <pre>
	 * public class NewCOEnvironment<E> extends StandardCOEnvironment<E> {
	 *    ...
	 * 
	 *    public COList<E> createCOList(URI id) throws ExistingCOEntityException {
	 *       return super.createCOList(new MyOwnCOListImplementation(id, this));
	 *    }
	 *    
	 *    public COList<E> createCOList() {
	 *       return super.createCOList(new MyOwnCOListImplementation(null, this));
	 *    }
	 *    
	 *    ...
	 * }
	 * </pre>
	 * <p>Any element contained by the input list will be removed before returning.</p>
	 * 
	 * @param coll the list to be used for the creation.
	 * @return the input collection as part of the environment.
	 * @throws ExistingCOEntityException if the URI of the input collection has been previously used for another
	 * entity handled by this environment.
	 */
	protected COList<E> createCOList(COList<E> coll) throws ExistingCOEntityException {
		URI id = coll.getURI();
		coll.clear();
		
		check(id);
		
		if (id == null) {
			anonymousLists.add(coll);
		} else {
			uriLists.put(id, coll);
		}
		
		return coll; 
	}
	
	@Override
	public COList<E> createCOList(URI id) throws ExistingCOEntityException {
		check(id);
		
		COList<E> coll = new StandardCOList<E>(id, this);
		if (id == null) {
			anonymousLists.add(coll);
		} else {
			uriLists.put(id, coll);
		}
		
		return coll; 
	}
	
	/**
	 * <p>This method allows to use a different kinds of implementation of {@code COBag}, rather than
	 * {@code StandardCOBag} that is used as default by this class.</p>
	 * <p>This method becomes particularly useful when we want to extend this class keeping the same
	 * behaviour except of using different kind of collection implementations. For bags, this
	 * can be easily implemented by overriding the public creation methods for bags as follows:</p>
	 * <pre>
	 * public class NewCOEnvironment<E> extends StandardCOEnvironment<E> {
	 *    ...
	 * 
	 *    public COBag<E> createCOBag(URI id) throws ExistingCOEntityException {
	 *       return super.createCOBag(new MyOwnCOBagImplementation(id, this));
	 *    }
	 *    
	 *    public COBag<E> createCOBag() {
	 *       return super.createCOBag(new MyOwnCOBagImplementation(null, this));
	 *    }
	 *    
	 *    ...
	 * }
	 * </pre>
	 * <p>Any element contained by the input bag will be removed before returning.</p>
	 * 
	 * @param coll the bag to be used for the creation.
	 * @return the input collection as part of the environment.
	 * @throws ExistingCOEntityException if the URI of the input collection has been previously used for another
	 * entity handled by this environment.
	 */
	protected COBag<E> createCOBag(COBag<E> coll) throws ExistingCOEntityException {
		URI id = coll.getURI();
		coll.clear();
		
		check(id);
		
		if (id == null) {
			anonymousBags.add(coll);
		} else {
			uriBags.put(id, coll);
		}
		
		return coll; 
	}

	@Override
	public COBag<E> createCOBag(URI id) throws ExistingCOEntityException {
		check(id);
		
		COBag<E> coll = new StandardCOBag<E>(id, this);
		if (id == null) {
			anonymousBags.add(coll);
		} else {
			uriBags.put(id, coll);
		}
		
		return coll; 
	}

	/**
	 * <p>This method allows to use a different kinds of implementation of {@code COSet}, rather than
	 * {@code StandardCOSet} that is used as default by this class.</p>
	 * <p>This method becomes particularly useful when we want to extend this class keeping the same
	 * behaviour except of using different kind of collection implementations. For sets, this
	 * can be easily implemented by overriding the public creation methods for sets as follows:</p>
	 * <pre>
	 * public class NewCOEnvironment<E> extends StandardCOEnvironment<E> {
	 *    ...
	 * 
	 *    public COSet<E> createCOSet(URI id) throws ExistingCOEntityException {
	 *       return super.createCOSet(new MyOwnCOSetImplementation(id, this));
	 *    }
	 *    
	 *    public COSet<E> createCOSet() {
	 *       return super.createCOSet(new MyOwnCOSetImplementation(null, this));
	 *    }
	 *    
	 *    ...
	 * }
	 * </pre>
	 * <p>Any element contained by the input set will be removed before returning.</p>
	 * 
	 * @param coll the set to be used for the creation.
	 * @return the input collection as part of the environment.
	 * @throws ExistingCOEntityException if the URI of the input collection has been previously used for another
	 * entity handled by this environment.
	 */
	protected COSet<E> createCOSet(COSet<E> coll) throws ExistingCOEntityException {
		URI id = coll.getURI();
		coll.clear();
		
		check(id);
		
		if (id == null) {
			anonymousSets.add(coll);
		} else {
			uriSets.put(id, coll);
		}
		
		return coll; 
	}
	
	@Override
	public COSet<E> createCOSet(URI id) throws ExistingCOEntityException {
		check(id);
		
		COSet<E> coll = new StandardCOSet<E>(id, this);
		if (id == null) {
			anonymousSets.add(coll);
		} else {
			uriSets.put(id, coll);
		}
		
		return coll;
	}
	
	@Override
	public COList<E> createCOList() {
		COList<E> coll = new StandardCOList<E>(null, this);
		anonymousLists.add(coll);
		return coll;
	}

	@Override
	public COBag<E> createCOBag() {
		COBag<E> coll = new StandardCOBag<E>(null, this);
		anonymousBags.add(coll);
		return coll;
	}

	@Override
	public COSet<E> createCOSet() {
		COSet<E> coll = new StandardCOSet<E>(null, this);
		anonymousSets.add(coll);
		return coll;
	}
	
	/**
	 * This method checks if an URI has been already used as CO entity identifier of this environment.
	 * 
	 * @param id the URI to check.
	 * @throws ExistingCOEntityException if the id already exists.
	 */
	private void check(URI id) throws ExistingCOEntityException {
		if (id != null && isCOEntityInEnvironment(id)) {
			throw new ExistingCOEntityException("The URI '" + id + "' is used for a CO entity already parts of" +
					" this environment");
		}
	}

	@Override
	public COCollection<E> getCollection(URI id) {
		COCollection<E> result = uriLists.get(id);
		if (result == null) {
			result = uriSets.get(id);
			if (result == null) {
				result = uriBags.get(id);
			}
		}
		
		return result;
	}

	@Override
	public Set<COCollection<E>> elementOf(E o) {
		Set<COCollection<E>> result = new HashSet<COCollection<E>>();
		
		HashSet<COCollection<E>> set = new HashSet<COCollection<E>>();
		set.addAll(uriSets.values());
		set.addAll(uriBags.values());
		set.addAll(uriLists.values());
		set.addAll(anonymousSets);
		set.addAll(anonymousBags);
		set.addAll(anonymousLists);
		
		Iterator<COCollection<E>> ite = set.iterator();
		while (ite.hasNext()) {
			COCollection<E> currentCollection = ite.next();
			if (currentCollection.elements().contains(o)) {
				result.add(currentCollection);
			}
		}
		
		return result;
	}

	@Override
	public Set<COItem<E>> itemContentOf(E o) {
		Set<COItem<E>> result = new HashSet<COItem<E>>();
		
		HashSet<COBag<E>> set = new HashSet<COBag<E>>();
		set.addAll(uriBags.values());
		set.addAll(uriLists.values());
		set.addAll(anonymousBags);
		set.addAll(anonymousLists);
		
		Iterator<COBag<E>> ite = set.iterator();
		while (ite.hasNext()) {
			COBag<E> currentBag = ite.next();
			result.addAll(currentBag.items(o));
		}
		
		return result;
	}

	@Override
	public boolean removeCollection(COCollection<E> coll) {
		URI id = coll.getURI();
		
		boolean result = getCollection(id) != null;
		
		if (result) {
			uriSets.remove(id);
			uriBags.remove(id);
			uriLists.remove(id);
			anonymousSets.remove(coll);
			anonymousBags.remove(coll);
			anonymousLists.remove(coll);
		}
		
		return result;
	}

	@Override
	public boolean isCOEntityInEnvironment(URI id) {
		boolean result = getCollection(id) != null;
		
		if (!result) {
			HashSet<COBag<E>> set = new HashSet<COBag<E>>();
			set.addAll(uriBags.values());
			set.addAll(uriLists.values());
			
			Iterator<COBag<E>> ite = set.iterator();
			while (!result && ite.hasNext()) {
				result = ite.next().itemURIs().contains(id);
			}
		}
		
		return result;
	}

	@Override
	public Set<COCollection<E>> getCollections() {
		HashSet<COCollection<E>> set = new HashSet<COCollection<E>>();
		set.addAll(uriSets.values());
		set.addAll(uriBags.values());
		set.addAll(uriLists.values());
		set.addAll(anonymousSets);
		set.addAll(anonymousBags);
		set.addAll(anonymousLists);
		
		return set;
	}
}
