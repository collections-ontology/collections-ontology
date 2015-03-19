**Set example #1**
## Modeling the Contributors of Collections Ontology ##

Simplest representation of the set:

```
# Snippet 1

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix co:  	 <http://purl.org/co/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.

<http://purl.org/co/example/1/set/1>
 	a co:Set;
 	co:element <http://www.hcklab.org/foaf.rdf#me> ;
 	co:element :a1 ;
 	co:element :a2 .
 		
<http://www.hcklab.org/foaf.rdf#me> 
    a foaf:Person ;
    foaf:firstName "Paolo" ;
    foaf:lastName "Ciccarese" .
    
:a1
    a foaf:Person ;
    foaf:firstName "Marco" ;
    foaf:lastName "Ocana" .
    
:a2
    a foaf:Person ;
    foaf:firstName "Silvio" ;
    foaf:lastName "Peroni" .
```

### Adding provenance and labels ###
It is possible to add labels and provenance data using the desired vocabulary. For example, the following file has been created manually by one of the contributors of the CO ontology.

```
# Snippet 2

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>.
@prefix dct:     <http://purl.org/dc/terms/>.
@prefix co:  	 <http://purl.org/co/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.

<http://purl.org/co/example/1/set/1>
 	a co:Set;
 	rdfs:label "Collections Ontology Contributors" ;
        dct:created "2011-03-25" ;
 	dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
 	co:element <http://www.hcklab.org/foaf.rdf#me> ;
 	co:element :a1 ;
 	co:element :a2 .
 		
<http://www.hcklab.org/foaf.rdf#me> 
    a foaf:Person ;
    rdfs:label "Paolo Ciccarese" ;
    foaf:firstName "Paolo" ;
    foaf:lastName "Ciccarese" .
    
:a1
    a foaf:Person ;
    rdfs:label "Marco Ocana" ;
    foaf:firstName "Marco" ;
    foaf:lastName "Ocana" .
    
:a2
    a foaf:Person ;
    rdfs:label "Silvio Peroni" ;
    foaf:firstName "Silvio" ;
    foaf:lastName "Peroni" .
```