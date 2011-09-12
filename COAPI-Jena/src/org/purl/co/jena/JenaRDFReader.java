package org.purl.co.jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.purl.co.classes.StandardCOEnvironment;
import org.purl.co.exceptions.ExistingCOEntityException;
import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COReader;
import org.purl.co.interfaces.COSet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class JenaRDFReader implements COReader<Resource> {

	private final String co = "http://purl.org/co/";
	
	@Override
	public COEnvironment<Resource> read(String content) {
		Model model = ModelFactory.createDefaultModel();
		StringReader input = new StringReader(content);
		
		try {
			model.read(input, null);
		} catch (Exception e) {
			input = new StringReader(content);
			model.read(input, null, "TURTLE");
		}
		
		return read(model);
	}

	@Override
	public COEnvironment<Resource> read(File f) throws FileNotFoundException {
		Model model = ModelFactory.createDefaultModel();
		FileInputStream input = new FileInputStream(f); 
		
		try {
			model.read(input, null);
		} catch (Exception e) {
			input = new FileInputStream(f);
			model.read(input, null, "TURTLE");
		}
		
		return read(model);
	}

	private COEnvironment<Resource> read(Model m) {
		COEnvironment<Resource> env = new StandardCOEnvironment<Resource>();
		
		/* Find all the sets in the RDF graph */
		Set<Resource> sets = findAllSets(m);
		
		/* Find all the lists in the RDF graph */
		Set<Resource> lists = findAllLists(m);
		
		/* Find all the bags in the RDF graph */
		Set<Resource> bags = findAllBags(m, lists);
		
		buildSets(sets, m, env);
		buildBags(bags, m, env);
		buildLists(lists, m, env);
		
		return env;
	}

	private void buildLists(Set<Resource> lists, Model m,
			COEnvironment<Resource> env) {
		for (Resource list : lists) {
			COList<Resource> coList = null;
			if (list.isAnon()) {
				coList = env.createCOList();
			} else {
				try {
					coList = env.createCOList(URI.create(list.getURI()));
				} catch (ExistingCOEntityException e) {
					System.err.println("The resource '" + list.getURI() + "' is already in the environment");
				}
			}
			
			Resource item = lookForFirstItem(list, m);
			while (item != null) {
				coList.add(getItemContent(item,m));
				item = lookForNextItem(item, m);
			}
		}
	}
	
	private Resource lookForFirstItem(Resource coList, Model m) {
		Resource result = null;
		
		ExtendedIterator<RDFNode> ite = m.listObjectsOfProperty(coList, m.createProperty(co+"firstItem")).andThen(
				m.listSubjectsWithProperty(m.createProperty(co+"firstItemOf"), coList));
		while (ite.hasNext() && result == null) {
			RDFNode first = ite.next();
			if (first.isResource()) {
				result = (Resource) first;
			}
		}
		
		/* I didn't find the first item */
		if (result == null) {
			ExtendedIterator<RDFNode> iteItems = getAllItems(coList, m);
			while (iteItems.hasNext() && result == null) {
				RDFNode item = iteItems.next();
				if (item.isResource()) {
					int index = getIndex((Resource) item, m);
					if (index == 1) { /* Look for the index */
						result = (Resource) item;
					} else if (index == 0) { /* Look for precedings */
						if (
								!m.listObjectsOfProperty(coList, m.createProperty(co+"nextItem")).andThen(
								m.listSubjectsWithProperty(m.createProperty(co+"previousItem"), coList)).hasNext()) {
							result = (Resource) item;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	private Resource getItemContent(Resource coItem, Model m) {
		Resource result = null;
		ExtendedIterator<RDFNode> ite = m.listObjectsOfProperty(coItem, m.createProperty(co+"itemContent")).andThen(
				m.listSubjectsWithProperty(m.createProperty(co+"itemContentOf"), coItem));
		while (ite.hasNext() && result == null) {
			RDFNode element = ite.next();
			if (element.isResource()) {
				result = (Resource) element;
			}
		}
		return result;
	}
	
	private ExtendedIterator<RDFNode> getAllItems(Resource coBag, Model m) {
		return m.listObjectsOfProperty(coBag, m.createProperty(co+"item")).andThen(
				m.listSubjectsWithProperty(m.createProperty(co+"itemOf"), coBag)).andThen(
						m.listObjectsOfProperty(coBag, m.createProperty(co+"firstItem"))).andThen(
								m.listSubjectsWithProperty(m.createProperty(co+"firstItemOf"), coBag)).andThen(
										m.listObjectsOfProperty(coBag, m.createProperty(co+"lastItem"))).andThen(
												m.listSubjectsWithProperty(m.createProperty(co+"lastItemOf"), coBag));
	}
	
	private int getIndex(Resource coItem, Model m) {
		int result = 0;
		
		NodeIterator ite = m.listObjectsOfProperty(coItem, m.createProperty(co+"index"));
		while (ite.hasNext()) {
			RDFNode candidate = ite.next();
			if (candidate.isLiteral()) {
				result = candidate.asLiteral().getInt();
			}
		}
		
		return result;
	}
	
	private Resource lookForNextItem(Resource coItem, Model m) {
		Resource result = null;
		
		ExtendedIterator<RDFNode> ite = m.listObjectsOfProperty(coItem, m.createProperty(co+"nextItem")).andThen(
				m.listSubjectsWithProperty(m.createProperty(co+"previousItem"), coItem));
		while (ite.hasNext() && result == null) {
			RDFNode item = ite.next();
			if (item.isResource()) {
				result = (Resource) item;
			}
		}
		
		if (result == null) {
			int index = getIndex(coItem, m);
			if (index > 0) {
				ExtendedIterator<RDFNode> iteList = 
					m.listObjectsOfProperty(coItem, m.createProperty(co+"itemOf")).andThen(
						m.listSubjectsWithProperty(m.createProperty(co+"item"), coItem));
				while (iteList.hasNext() && result == null) {
					RDFNode list = iteList.next();
					if (list.isResource()) {
						ResIterator iteItem = 
							m.listSubjectsWithProperty(m.createProperty(co+"index"), new Integer(index+1).toString());
						while (iteItem.hasNext() && result == null) {
							Resource item = iteItem.next();
							if (
									m.listStatements(item, m.createProperty(co+"itemOf"), list).andThen(
									m.listStatements((Resource) list, m.createProperty(co+"item"), item)).hasNext()) {
								result = item;
							}
						}
					}
				}
			}
		}
		
		return result;
	}

	private void buildBags(Set<Resource> bags, Model m,
			COEnvironment<Resource> env) {
		for (Resource bag : bags) {
			COBag<Resource> coBag = null;
			if (bag.isAnon()) {
				coBag = env.createCOBag();
			} else {
				try {
					coBag = env.createCOBag(URI.create(bag.getURI()));
				} catch (ExistingCOEntityException e) {
					System.err.println("The resource '" + bag.getURI() + "' is already in the environment");
				}
			}
			
			ExtendedIterator<RDFNode> ite = getAllItems(bag, m);
			Set<Resource> visitedItems = new HashSet<Resource>();
			while (ite.hasNext()) {
				RDFNode item = ite.next();
				if (item.isResource() && !visitedItems.contains(item)) {
					visitedItems.add((Resource) item);
					ExtendedIterator<RDFNode> iteElement = m.listObjectsOfProperty((Resource) item,
							m.createProperty(co+"itemContent")).andThen(
									m.listSubjectsWithProperty(m.createProperty(co+"itemContentOf"), (Resource)item));
					while (iteElement.hasNext()) {
						RDFNode candidateElement = iteElement.next();
						if (candidateElement.isResource()) {
							coBag.add((Resource) candidateElement);
						}
					}
				}
			}
		}
	}

	private void buildSets(Set<Resource> sets, Model m,
			COEnvironment<Resource> env) {
		for (Resource set : sets) {
			COSet<Resource> coSet = null;
			if (set.isAnon()) {
				coSet = env.createCOSet();
			} else {
				try {
					coSet = env.createCOSet(URI.create(set.getURI()));
				} catch (ExistingCOEntityException e) {
					System.err.println("The resource '" + set.getURI() + "' is already in the environment");
				}
			}
			
			ExtendedIterator<RDFNode> ite = m.listObjectsOfProperty(set, m.createProperty(co+"element")).andThen(
					m.listSubjectsWithProperty(m.createProperty(co+"elementOf"), set));
			while (ite.hasNext()) {
				RDFNode candidateElement = ite.next();
				if (candidateElement.isResource()) {
					coSet.add((Resource) candidateElement);
				}
			}
		}
	}

	private Set<Resource> findAllBags(Model m, Set<Resource> excludeLists) {
		Set<Resource> result = new HashSet<Resource>();
		
		ExtendedIterator<RDFNode> ite = m.listObjectsOfProperty(m.createProperty(co+"itemOf")).andThen(
				m.listSubjectsWithProperty(m.createProperty(co+"item"))).andThen(
						m.listSubjectsWithProperty(RDF.type, m.createResource(co+"Bag")));
		
		while (ite.hasNext()) {
			RDFNode candidateBag = ite.next();
			if (candidateBag.isResource() && !excludeLists.contains(candidateBag)) {
				result.add((Resource) candidateBag);
			}
		}
		
		return result;
	}

	private Set<Resource> findAllLists(Model m) {
		Set<Resource> result = new HashSet<Resource>();
		
		ExtendedIterator<RDFNode> ite = 
			m.listObjectsOfProperty(m.createProperty(co+"firstItemOf")).andThen(
					m.listObjectsOfProperty(m.createProperty(co+"lastItemOf"))).andThen(
							m.listSubjectsWithProperty(RDF.type, m.createResource(co+"List")).andThen(
									m.listSubjectsWithProperty(m.createProperty(co+"firstItem"))).andThen(
											m.listSubjectsWithProperty(m.createProperty(co+"lastItem"))));
		while (ite.hasNext()) {
			RDFNode list = ite.next();
			if (list.isResource()) {
				result.add((Resource) list);
			}
		}
		
		ExtendedIterator<Statement> iteStat = m.listStatements(
				null,m.createProperty(co+"item"), (RDFNode) null).andThen(
						m.listStatements(null,m.createProperty(co+"itemOf"), (RDFNode) null));
		while (iteStat.hasNext()) {
			Statement statement = iteStat.next();
			Resource item = null;
			Resource candidateList = null;
			if (statement.getPredicate().equals(m.createProperty(co+"item"))) {
				item = (Resource) statement.getObject();
				candidateList = statement.getSubject();
			} else {
				item = statement.getSubject();
				candidateList = (Resource) statement.getObject();
			}
			
			if (
					m.listResourcesWithProperty(m.createProperty(co+"nextItem"),item).hasNext() ||
					m.listResourcesWithProperty(m.createProperty(co+"previousItem"),item).hasNext() ||
					m.listResourcesWithProperty(m.createProperty(co+"precededBy"),item).hasNext() ||
					m.listResourcesWithProperty(m.createProperty(co+"followedBy"),item).hasNext() ||
					m.listResourcesWithProperty(m.createProperty(co+"nextItem"),item).hasNext() ||
					m.listResourcesWithProperty(m.createProperty(co+"index"),item).hasNext()) {
				result.add(candidateList);
			}
			
		}
		
		return result;
	}

	private Set<Resource> findAllSets(Model m) {
		Set<Resource> result = new HashSet<Resource>();
		
		ResIterator ite = m.listSubjectsWithProperty(RDF.type, m.createResource(co+"Set"));
		while (ite.hasNext()) {
			result.add(ite.next());
		}
		
		return result;
	}
}
