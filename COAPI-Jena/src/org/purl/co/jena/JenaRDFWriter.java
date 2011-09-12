package org.purl.co.jena;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COCollection;
import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COItem;
import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COListItem;
import org.purl.co.interfaces.COSet;
import org.purl.co.interfaces.COWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * This class implements a {@code COWriter} conforming it to Jena {@code Resource}s.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
public class JenaRDFWriter implements COWriter<Resource> {
	
	/**
	 * All the possible formats to be used.
	 */
	public enum Format {
		/**
		 * Turtle format.
		 */
		Turtle,
		/**
		 * RDF/XML format.
		 */
		RDFXML
	}
	
	private String format = null;
	
	/**
	 * It constructs a new writer based of the RDF model in Jena.
	 * 
	 * @param f the format to be used.
	 */
	public JenaRDFWriter(Format f) {
		if (f == Format.Turtle) {
			format = "TURTLE";
		} else if (f == Format.RDFXML) {
			format = "RDF/XML";
		}
	}
	
	@Override
	public String getStringRepresentation(COEnvironment<Resource> env) {
		String result = null;
		
		Model model = getModel(env);
		try {
			StringWriter writer = new StringWriter();
			model.write(writer, format);
			result = writer.toString();
		} catch (NullPointerException e) {
			// Do nothing
		}
		
		return result;
	}

	@Override
	public void store(COEnvironment<Resource> env, File f) {
		Model model = getModel(env);
		try {
			model.write(new FileWriter(f), format);
		} catch (IOException e) {
			// Do nothing
		}
	}
	
	/**
	 * This method sets all the prefixes for the model.
	 * 
	 * @param model the model where setting the prefixes.
	 */
	private void setPrefixes(Model model) {
		model.setNsPrefix("co", "http://purl.org/co/");
		model.setNsPrefix("xs", "http://www.w3.org/2001/XMLSchema#");
		model.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
		model.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
	}
	
	/**
	 * This method makes a Jena RDF model starting from a {COEnvironment} of {@code Resource}s.
	 * @param env the environment to be converted.
	 * @return the Jena RDF model result of the conversion.
	 */
	private Model getModel(COEnvironment<Resource> env) {
		Model m = ModelFactory.createDefaultModel();
		Property size = m.createProperty(COCollection.size.toString());
		
		for (COCollection<Resource> coll : env.getCollections()) {
			Resource resource = null;
			URI id = coll.getURI();
			if (id == null) {
				resource = m.createResource();
			} else {
				resource = m.createResource(id.toString());
			}
			
			m.add(resource, size, new Integer(coll.size()).toString());
			
			if (coll instanceof COSet) {
				handleSet(m, (COSet<Resource>) coll, resource);
			}
			else if (coll instanceof COList) {
				handleList(m, (COList<Resource>) coll, resource);
			} else {
				handleBag(m, (COBag<Resource>) coll, resource);
			}
		}
		
		setPrefixes(m);
		return m;
	}

	/**
	 * This method adds all the statements concerning CO lists to the model specified.
	 * @param m the model where adding the list statements.
	 * @param coll the list considered.
	 * @param resource the representation in Jena {@code Resource} the the list considered.
	 */
	private void handleList(Model m, COList<Resource> coll, Resource resource) {
		Resource type = m.createResource(COList.List.toString());
		m.add(resource, RDF.type, type);
		
		COListItem<Resource> first = coll.firstItem();
		if (first != null) {
			handleListItem(m, first, resource);
		}
	}
	
	/**
	 * This method adds all the statements concerning CO bags to the model specified.
	 * @param m the model where adding the bag statements.
	 * @param coll the bag considered.
	 * @param resource the representation in Jena {@code Resource} the the bag considered.
	 */
	private void handleBag(Model m, COBag<Resource> coll, Resource resource) {
		if (!(coll instanceof COList)) {
			Resource type = m.createResource(COBag.Bag.toString());
			m.add(resource, RDF.type, type);
		}
		
		Property item = m.createProperty(COBag.item.toString());
		for (COItem<Resource> coItem : coll.items()) {
			m.add(resource, item, handleItem(m, coItem));
		}
	}
	
	/**
	 * This method adds all the statements concerning CO items to the model specified.
	 * @param m the model where adding the item statements.
	 * @param coItem the item considered.
	 */
	private Resource handleItem(Model m, COItem<Resource> coItem) {
		Resource i = null;
		
		URI id = coItem.getCOId();
		if (id == null) {
			i = m.createResource();
		} else {
			i = m.createResource(id.toString());
		}
		
		m.add(i, RDF.type, m.createResource(COItem.Item.toString()));
		Property itemContent = m.createProperty(COItem.itemContent.toString());
		m.add(i, itemContent, coItem.itemContent());
		
		return i;
	}
	
	/**
	 * This method adds all the statements concerning CO list items to the model specified.
	 * @param m the model where adding the list item statements.
	 * @param coItem the list item considered.
	 */
	private Resource handleListItem(Model m, COListItem<Resource> coItem, Resource list) {
		Resource i = null;
		
		URI id = coItem.getCOId();
		if (id == null) {
			i = m.createResource();
		} else {
			i = m.createResource(id.toString());
		}
		
		m.add(i, RDF.type, m.createResource(COListItem.ListItem.toString()));
		
		boolean isFirst , isLast = false;
		if (isFirst = coItem.firstItemOf() != null) {
			Property firstItem = m.createProperty(COList.firstItem.toString());
			m.add(list, firstItem, i);
		}
		if (isLast = coItem.lastItemOf() != null) {
			Property lastItem = m.createProperty(COList.lastItem.toString());
			m.add(list, lastItem, i);
		}
		if (!isFirst && !isLast) {
			Property item = m.createProperty(COBag.item.toString());
			m.add(list, item, i);
		}
		Property itemContent = m.createProperty(COItem.itemContent.toString());
		m.add(i, itemContent, coItem.itemContent());
		
		COListItem<Resource> next = ((COListItem<Resource>) coItem).nextItem();
		if (next != null) {
			Property nextItem = m.createProperty(COListItem.nextItem.toString());
			m.add(i, nextItem, handleListItem(m, next, list));
		}
		
		return i;
	}

	/**
	 * This method adds all the statements concerning CO sets to the model specified.
	 * @param m the model where adding the sets statements.
	 * @param coll the sets considered.
	 * @param resource the representation in Jena {@code Resource} the the sets considered.
	 */
	private void handleSet(Model m, COSet<Resource> coll, Resource resource) {
		Resource type = m.createResource(COSet.Set.toString());
		m.add(resource, RDF.type, type);
		
		Property element = m.createProperty(COCollection.element.toString());
		for (Resource r : coll) {
			m.add(resource, element, r);
		}
	}
}
