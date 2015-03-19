## The ORE specification and its features ##
The [Open Reuse and Exchange specification](http://www.openarchives.org/ore/1.0/datamodel) (ORE specification) is a standard defined by the [Open Archives Initiative](http://www.openarchives.org/) for describing and exchanging aggregations of Web resources.

The main concept of this specification is the _Aggregation_, i.e., a particular resource that aggregates, either logically or physically, other resources, as shown in the following figure:

![http://www.openarchives.org/ore/1.0/datamodel-images/ReMAggregates.jpg](http://www.openarchives.org/ore/1.0/datamodel-images/ReMAggregates.jpg)

It is possible to use particular kinds of resources, called [proxies](http://www.openarchives.org/ore/1.0/datamodel#Proxy), so as to refer to a specific aggregated resource in a context of a particular aggregation.

Moreover, by using proxies, we can specify an order (with an external vocabulary) for aggregated resources of an aggregation, if needed, as shown in the following figure:

![http://www.openarchives.org/ore/1.0/datamodel-images/Proxy.jpg](http://www.openarchives.org/ore/1.0/datamodel-images/Proxy.jpg)

All these scenarios can be easily integrated with CO.

## Set of cited papers ##
Our personal scientific library, composed by a large number of works, is actually an aggregation (and a set) of different papers. This scenario can be described in OWL through both ORE and CO as follows:

```
@prefix ore: <http://www.openarchives.org/ore/items/> .
@prefix co:  <http://purl.org/co/> .
@prefix doi: <http://dx.doi.org/> .
@prefix :    <http://www.example.com/> .

:myOwnLibrary a ore:Aggregation
    ; ore:aggregates
          doi:10.1080/13614568.2010.497194 
        , doi:10.1016/j.jbi.2008.04.010 .

:myOwnLibrary a co:Set
    ; co:element
          doi:10.1080/13614568.2010.497194 
        , doi:10.1016/j.jbi.2008.04.010 .
```

In the previous snippet both ore:Aggregation and co:Set are used to describe the composition of a set of papers through two different vocabularies. A much compact definition of these assertions can be easily done by adding a bit of ontological alignment between the two ontologies and, then, inferring what is not explicitly asserted. For instance, if the following Manchester Syntax extension of ORE is considered:

```
Prefix: ore: <http://www.openarchives.org/ore/items/>
Prefix: co:  <http://purl.org/co/>

Class: ore:Aggregation
    EquivalentTo: co:Set

ObjectProperty: ore:aggregates
    EquivalentTo: co:element
```

we can infer all the remaining statements starting from the following ones:

```
@prefix ore: <http://www.openarchives.org/ore/items/> .
@prefix doi: <http://dx.doi.org/> .
@prefix :    <http://www.example.com/> .

:myOwnLibrary a ore:Aggregation
    ; ore:aggregates
          doi:10.1080/13614568.2010.497194 
        , doi:10.1016/j.jbi.2008.04.010 .
```

## Bibliographic references of a paper ##
When we are writing a scientific paper, we use to refer to bibliographic references, each of them referencing a precise paper, for explicitly citing other works in our paper. Of course, two bibliographic references, even when defined in two different papers and referring to the same work, can have associated particular (and contextual) metadata that change reference by reference. This scenario can be described in OWL through both ORE and CO as follows:

```
@prefix ore:     <http://www.openarchives.org/ore/items/> .
@prefix w3:      <http://www.w3.org/TR/> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix :        <http://www.example.com/> .

:paperOneBibligraphicReferenceCollection a ore:Aggregation
    ; ore:aggregates w3:owl2-syntax , w3:rdf-concepts .

:proxy1 a ore:Proxy 
    ; ore:proxyIn :paperOneBibligraphicReferenceCollection
    ; ore:proxyFor w3:owl2-syntax
    ; dcterms:bibliographicCitation "Motik, B. et al (2009). OWL 2 Web Ontology Language
      Structural Specification and Functional-Style Syntax" .

:proxy2 a ore:Proxy 
    ; ore:proxyIn :paperOneBibligraphicReferenceCollection
    ; ore:proxyFor w3:rdf-concepts
    ; dcterms:bibliographicCitation "Klyne, G. et al (2004). Resource Description Framework (RDF):
      Concepts and Abstract Syntax" .

:paperTwoBibligraphicReferenceCollection a ore:Aggregation
    ; ore:aggregates w3:owl2-syntax , w3:rdf-sparql-query .

:proxy3 a ore:Proxy
    ; ore:proxyIn :paperTwoBibligraphicReferenceCollection
    ; ore:proxyFor w3:owl2-syntax
    ; dcterms:bibliographicCitation "OWL 2 Web Ontology Language Structural,
      W3C Recommendation 27 October 2009" .

:proxy4 a ore:Proxy 
    ; ore:proxyIn :paperTwoBibligraphicReferenceCollection
    ; ore:proxyFor w3:rdf-sparql-query
    ; dcterms:bibliographicCitation "SPARQL Query Language for RDF,
      W3C Recommendation 15 January 2008" .

:paperOneBibligraphicReferenceCollection a co:Bag
    ; co:item :proxy1 , :proxy2 .

:proxy1 a co:Item
    ; co:itemContent w3:owl2-syntax .

:proxy2 a co:Item
    ; co:itemContent w3:rdf-concepts .

:paperTwoBibligraphicReferenceCollection a co:Bag
    ; co:item :proxy3 , :proxy4 .

:proxy3 a co:Item
    ; co:itemContent w3:owl2-syntax .

:proxy4 a co:Item
    ; co:itemContent w3:rdf-sparql-query .
```

In the previous snippet both ore:Aggregation and co:Bag are used to describe two bibliographic reference collections through two different vocabularies. A much compact definition of these assertions can be easily done by adding a bit of ontological alignment between the two ontologies and, then, inferring what is not explicitly asserted. For instance, modifying the previously-defined Manchester Syntax extension of ORE as follows:

```
Prefix: ore: <http://www.openarchives.org/ore/items/>
Prefix: co:  <http://purl.org/co/>

Class: ore:Aggregation
    EquivalentTo: co:Set or 
                (co:Bag that co:item only ore:Proxy)

ObjectProperty: ore:aggregates
    EquivalentTo: co:element

ObjectProperty: ore:proxyIn
    EquivalentTo: co:itemOf

ObjectProperty: ore:proxyFor
    EquivalentTo: co:itemContent
```

In this way, we can infer all the remaining statements starting from the following ones:

```
@prefix ore:     <http://www.openarchives.org/ore/items/> .
@prefix w3:      <http://www.w3.org/TR/> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix :        <http://www.example.com/> .

:paperOneBibligraphicReferenceCollection a ore:Aggregation
    ; co:item
          [ ore:proxyFor w3:owl2-syntax
              ; dcterms:bibliographicCitation "Motik, B. et al (2009). OWL 2 Web Ontology Language
                Structural Specification and Functional-Style Syntax" ]
        , [ ore:proxyFor w3:rdf-concepts
              ; dcterms:bibliographicCitation "Klyne, G. et al (2004). Resource Description Framework (RDF):
                Concepts and Abstract Syntax" ] .

:paperTwoBibligraphicReferenceCollection a ore:Aggregation
    ; co:item 
          [ ore:proxyFor w3:owl2-syntax
              ; dcterms:bibliographicCitation "OWL 2 Web Ontology Language Structural,
                W3C Recommendation 27 October 2009" ]
        , [ ore:proxyFor w3:rdf-sparql-query
              ; dcterms:bibliographicCitation "SPARQL Query Language for RDF,
                W3C Recommendation 15 January 2008" ] .
```

## Then, order it: bibliographic reference lists ##
Usually, all the bibliographic references described by a particular paper are grouped in a specific section and listed considering a specific order. ORE gives just a recommendation (the property _xxx:hasNext_ of the previous figure) for keeping proxies in order, without defining a precise vocabulary to deal with this issue. Of course, co:List can be easily use to handle order between proxies, as shown as follows:

```
@prefix ore:     <http://www.openarchives.org/ore/items/> .
@prefix w3:     <http://www.w3.org/TR/> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix :        <http://www.example.com/> .

:paperOneBibliographicReferenceCollection a ore:Aggregation
    ; ore:aggregates w3:owl2-syntax , w3:rdf-concepts .

:proxy1 a ore:Proxy 
    ; ore:proxyFor w3:owl2-syntax
    ; dcterms:bibliographicCitation "Motik, B. et al (2009). OWL 2 Web Ontology Language
      Structural Specification and Functional-Style Syntax" .

:proxy2 a ore:Proxy 
    ; ore:proxyFor w3:rdf-concepts
    ; dcterms:bibliographicCitation "Klyne, G. et al (2004). Resource Description Framework (RDF):
      Concepts and Abstract Syntax" .

:paperOneBibliographicReferenceCollection a co:List
    ; co:firstItem :proxy1
    ; co:lastItem : proxy2 .

:proxy1 a co:ListItem
    ; co:nextItem :proxy2
    ; co:itemContent w3:owl2-syntax .

:proxy2 a co:ListItem
    ; co:itemContent w3:rdf-concepts .
```

Of course, ore:Aggregation and co:List are here used in a very redundant way. A much compact definition of these assertions can be easily done by adding a bit of ontological alignment between the two ontologies and, then, inferring what is not explicitly asserted. For instance, modifying the previously-defined Manchester Syntax extension of ORE as follows:

```
Prefix: ore: <http://www.openarchives.org/ore/items/>
Prefix: co:  <http://purl.org/co/>

Class: ore:Aggregation
    EquivalentTo: co:Set or 
                (co:Bag that co:item only ore:Proxy) or
                (co:List that co:item only ore:Proxy)

ObjectProperty: ore:aggregates
    EquivalentTo: co:element

ObjectProperty: ore:proxyIn
    EquivalentTo: co:itemOf

ObjectProperty: ore:proxyFor
    EquivalentTo: co:itemContent
```

In this way, we can infer the remaining statements starting from the following ones:

```
@prefix ore:     <http://www.openarchives.org/ore/items/> .
@prefix w3:     <http://www.w3.org/TR/> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix :        <http://www.example.com/> .

:paperOneBibliographicReferenceCollection a ore:Aggregation
    ; co:firstItem [ ore:proxyFor w3:owl2-syntax
        ; dcterms:bibliographicCitation "Motik, B. et al (2009). OWL 2 Web Ontology Language
          Structural Specification and Functional-Style Syntax" 
        ; co:nextItem[ ore:proxyFor w3:rdf-concepts
            ; dcterms:bibliographicCitation "Klyne, G. et al (2004). Resource Description Framework (RDF):
              Concepts and Abstract Syntax" ] ] .
```