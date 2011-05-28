package org.purl.co.classes;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COSet;

/**
 * This class is the standard implementation of a {@code COSet} as an {@code HashSet}.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
@SuppressWarnings({ "serial" })
public class StandardCOSet<E> extends HashSet<E> implements COSet<E> {

	private URI id = null;
	
	/**
	 * It constructs a new CO set identifiable through an URI.
	 * 
	 * @param id the URI identifying the set.
	 * @param env the environment that created this set. 
	 */
	protected StandardCOSet(URI id, COEnvironment<E> env) {
		this.id = id;
	}
	
	@Override
	public Set<E> elements() {
		return new HashSet<E>(this);
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
