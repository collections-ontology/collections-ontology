package org.purl.co.jena;

import java.net.URI;

import org.purl.co.classes.StandardCOEnvironment;
import org.purl.co.exceptions.ExistingCOEntityException;
import org.purl.co.interfaces.COBag;
import org.purl.co.interfaces.COEnvironment;
import org.purl.co.interfaces.COList;
import org.purl.co.interfaces.COSet;
import org.purl.co.jena.JenaRDFWriter.Format;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class JenaTest {
	public static void main(String[] args) {
		Model m = ModelFactory.createDefaultModel();
		String uri = "http://www.example.com/";
		
		COEnvironment<Resource> env = new StandardCOEnvironment<Resource>();
		
		try {
			COList<Resource> l1 = env.createCOList(URI.create(uri+"l1"));
			l1.add(m.createResource(uri+"l1_o1"));
			l1.add(m.createResource(uri+"l1_o2"));
			l1.add(URI.create(uri+"l1_i3"), m.createResource(uri+"l1_o3"));
			
			COList<Resource> l2 = env.createCOList();
			l2.add(m.createResource());
			
			COBag<Resource> b1 = env.createCOBag(URI.create(uri+"b1"));
			b1.add(m.createResource(uri+"b1_o1"));
			b1.add(m.createResource(uri+"b1_o2"));
			b1.add(m.createResource(uri+"b1_o3"));
			b1.add(m.createResource(uri+"b1_o4"));
			b1.add(m.createResource(uri+"b1_o5"));
			
			COSet<Resource> s1 = env.createCOSet(URI.create(uri+"s1"));
			s1.add(m.createResource(uri+"s1_o1"));
			s1.add(m.createResource(uri+"s1_o2"));
			s1.add(m.createResource(uri+"s1_o3"));
			
			System.out.println(new JenaRDFWriter(Format.Turtle).getStringRepresentation(env));
		} catch (ExistingCOEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
