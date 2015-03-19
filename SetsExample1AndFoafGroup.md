### How would _CO:Set_ integrate, for example, with _FOAF:Group_? ###
If you are a FOAF user, you might want to define the contributors to Collections Ontology as a FOAF (Friend Of A Friend) _Group_. Without Collections Ontology this would look something like:

```
# Snippet 1

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>.
@prefix dc:      <http://purl.org/dc/elements/1.1/#>.
@prefix dct:     <http://purl.org/dc/terms/>.
@prefix pav:     <http://purl.org/pav/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.

<http://purl.org/co/example/1/set/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # foaf
    a foaf:Group ;
    foaf:member <http://www.hcklab.org/foaf.rdf#me> ;
    foaf:member :a1 ;
    foaf:member :a2 ;
 		
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

The above example could be enough if your application is reacting to FOAF. However, if you want to introduce a level of abstraction and write code against Collections ontology structures one solution would be to use, at the same time, FOAF and Collections Ontology.

#### Approach #1: redundant statements (not recommended) ####

One possible approach consists in declaring a _FOAF:Group_ to be, at the same time, a _CO:Set_.

```
# Snippet 2

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>.
@prefix dc:      <http://purl.org/dc/elements/1.1/#>.
@prefix dct:     <http://purl.org/dc/terms/>.
@prefix pav:     <http://purl.org/pav/>.
@prefix co:  	 <http://purl.org/co/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.
@prefix foafx:   <http://blah.com/foafx/>.

<http://purl.org/co/example/1/group/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # foaf
    a foaf:Group ;
    foaf:member <http://www.hcklab.org/foaf.rdf#me> ;
    foaf:member :a1 ;
    foaf:member :a2 ;
    foafx:membersSet <http://purl.org/co/example/1/set/1> .

<http://purl.org/co/example/1/set/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # Collections Ontology 
    a co:Set ;
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

This approach is easy to implement but introduces a couple of issues: redundancies and some incompatibilities with the mathematical notion of Set. In fact, let's say we have two groups: 'The Golf Buddies' and the 'Authors of a piece of software'. These two groups have different identities, goals and life spans and therefore are identified different URIs. However, they happen to have the same exact members and, in terms of Set, they could ideally share the same URI.

In other words, we have a case of same set of members but still different groups. In mixing up the two concepts (Set and Group) we have an issue.

#### Approach #2: modeling the set separately ####
An alternative to the above approach, is to model _FOAF:Group_ and _CO:Set_ separately so that two different groups link the same set of members.

```
# Snippet 3

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>.
@prefix dc:      <http://purl.org/dc/elements/1.1/#>.
@prefix dct:     <http://purl.org/dc/terms/>.
@prefix pav:     <http://purl.org/pav/>.
@prefix co:  	 <http://purl.org/co/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.
@prefix foafx:   <http://blah.com/foafx/>.

<http://purl.org/co/example/1/group/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # foaf
    a foaf:Group ;
    foaf:member <http://www.hcklab.org/foaf.rdf#me> ;
    foaf:member :a1 ;
    foaf:member :a2 ;
    foafx:memberSet <http://purl.org/co/example/1/set/1> .

<http://purl.org/co/example/1/set/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # Collections Ontology 
    a co:Set ;
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

With this approach, if we introduce a second group with a different URI, we can still refer to the very same set of members.

```
# Snippet 4

...
<http://purl.org/co/example/1/group/2>
    rdfs:label "Golf Buddies" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # foaf
    a foaf:Group ;
    foaf:member <http://www.hcklab.org/foaf.rdf#me> ;
    foaf:member :a1 ;
    foaf:member :a2 ;
    foafx:memberSet <http://purl.org/co/example/1/set/1> .
...
```

### An erroneous way of leveraging inference ###
For some, it might be immediate to define some assertions (snippet 5) in order to treat the _foaf:Group_ as a _co:Set_ without altering the original code nor introducing redundancies. This approach might look attractive at first glance however it is not desirable.

Mathematically speaking a set is identified by the collections of its items. Therefore, if I define a second group named 'The golf buddies' with the very same members of the group 'Collections Ontology Contributors', we will end up having two different groups with the same set of people. Therefore it is desirable to keep groups and sets as separate entities.

```
# Snippet 5

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>.
@prefix dct:     <http://purl.org/dc/terms/>.
@prefix pav:     <http://purl.org/pav/>.
@prefix co:  	 <http://purl.org/co/>.
@prefix foaf:  	 <http://xmlns.com/foaf/0.1/>.

# mapping to infer the CO assertion from the FOAF ones
foaf:Group rdfs:subClassOf co:Set .
foaf:member rdfs:subPropertyOf co:element .

<http://purl.org/co/example/1/set/1>
    rdfs:label "Collections Ontology Contributors" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    dct:created "2011-03-25" ;
    # foaf
    a foaf:Group ;
    foaf:member <http://www.hcklab.org/foaf.rdf#me> ;
    foaf:member :a1 ;
    foaf:member :a2 .
 		
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