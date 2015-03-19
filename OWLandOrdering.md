## OWL and Ordering ##

OWL have no support for ordering, but the natural constructs from the underlying RDF vocabulary (rdf:List and rdf:nil) are unavailable in OWL-DL because they are used in its RDF serialization. In principle, rdf:Seq is not illegal but it depends on lexical ordering and has no logical semantics accessible to a DL classifier.

In other terms (1):
  1. The elements in a container are defined using the relations rdf:`_`1, rdf:`_`2, and so on that have no formal definition in RDF. Using them for the purpose of reasoning will require us to define and enforce the properties of these relations.
  1. It is not possible to define a container that has elements only of a specific type.
  1. For updating a specific element in a container in a remote source, one is forced to transmit the whole container.
  1. It is not possible to associate provenance information with the elements in a container.

But OWL has greater expressiveness than RDF (with constructs such as transitive properties) and reasoning capabilities (for checking consistency and inferring subsumption). Thus, the idea of reasoning with sequential structures in OWL-DL looks appealing.


The above content has been originally published in 2008  in [Paolo's blog](http://hcklab.blogspot.com/2008/12/moving-towards-swan-collections.html).

## Moving forward ##

Drummond et al. (2) proposed a way of representing sequential structures in OWL-DL. Analyzing the work of Hirsh & Kudenko (3) Drummond argued that "their representation requires extensive rewriting, the relation of the resulting structures to the original lists is not intuitive and, more importantly, the resulting structures grow as the square of the length of the list". Then, he describes a general list pattern, an intuitive approach related to that suggested by Hayes  and incorporated in the [Semantic Web Best Practice Working Group’s note on n-ary relations](http://www.w3.org/TR/swbp-n-aryRelations/)(4).

The list pattern works as follow:

![http://collections-ontology.googlecode.com/svn/trunk/images/OWLlistPattern.png](http://collections-ontology.googlecode.com/svn/trunk/images/OWLlistPattern.png)<br />
**Figure 1. OWL List Pattern**

Each item is held in a “cell” **OWLList**; each cell has 2 pointers, one to a head **hasContents - functional** and one to the tail cells **hasNext - functional**; the end of the list is indicated by a terminator **EmptyList** which also serves to represent the empty list. A transitive property, **isFollowedBy**, as a super-property of **hasNext** as been defined as well. In other words the members of any list are the contents of the first element plus the contents of all of the following elements. A separate OWL vocabulary has been defined as the RDF vocabulary cannot be used in OWL-DL.

Through the transitive property **followedBy** it is possible to ask things like: give me all the items that are **followedBy** "AC" for instance and it doesn't matter what is in between the item and the sequence itself.

In [Manchester Syntax](http://www.co-ode.org/resources/reference/manchester_syntax/):
```
Class(OWLList partial
  restriction(isFollowedBy allValuesFrom(OWLList)) 

Class(EmptyList complete
  OWLList restriction(hasContents maxCardinality(0)))

EquivalentClasses(
  EmptyList intersectionOf(
     OWLList NOT restriction(
        isFollowedBy SOME owl:Thing)))

ObjectProperty(hasListProperty 
   domain(OWLList))

ObjectProperty(hasContents 
   super(hasListProperty) Functional)

ObjectProperty(hasNext 
   super(isFollowedBy) Functional)

ObjectProperty(isFollowedBy 
   super(hasListProperty) Transitive range(OWLList))
     
```

OWLList can express:

![http://collections-ontology.googlecode.com/svn/trunk/images/OWLlistExpressiveness.png](http://collections-ontology.googlecode.com/svn/trunk/images/OWLlistExpressiveness.png)<br />
**Picture 2**: OWLList Expressiveness

For instance for the pattern (A**):
```
List_only_As --> 
  List AND
  hasContents ONLY A AND
  isFollowedBy ONLY (List AND hasContents ONLY A)
```**

Still, in OWL-DL there are a bunch of constraints that cannot be defined, and I would suggest to read the paper(2) for the complete list.

The above content has been originally published in 2008  in [Paolo's blog](http://hcklab.blogspot.com/2008/12/moving-towards-swan-collections_31.html).

### References ###
  1. Vinay K. Chaudhri, Bill Jarrold, John Pacheco. Exporting Knowledge Bases into OWL. OWL Experiences and Directions (OWLED 2006), Athens, Georgia, USA.
  1. Nicholas Drummond, Alan Rector, Robert Stevens, Georgina Moulton, Matthew Horridge, Hai Wang and Julian Sedenberg (2006). Putting OWL in Order: Paterns for sequences in OWL. OWL Experiences and Directions (OWLED 2006), Athens, Georgia, USA.
  1. Hirsh, H. and D. Kudenko. Representing Sequences in Description Logics. in Fourteenth National Conference on Artificial Intelligence. 1997.
  1. Noy, N.F. and A. Rector, [Defining N-ary Relations on the Semantic Web](http://www.w3.org/TR/swbp-n-aryRelations/). 2006, Semantic Web Best Practices Working Group Note, W3C.