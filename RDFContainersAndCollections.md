## RDF Containers ##
RDF allows the usage of three kinds of containers:

  * **rdf:Bag** - A Bag represents a group of resources or literals, possibly including duplicate members, where there is no significance in the order of the members. For example, a Bag might be used to describe a group of part numbers in which the order of entry or processing of the part numbers does not matter.
  * **rdf:Seq** - A Sequence or Seq represents a group of resources or literals, possibly including duplicate members, where the order of the members is significant. For example, a Sequence might be used to describe a group that must be maintained in alphabetical order.
  * **rdf:Alt** - An Alternative or Alt represents a group of resources or literals that are alternatives (typically for a single value of a property). For example, an Alt might be used to describe alternative language translations for the title of a book, or to describe a list of alternative Internet sites at which a resource might be found. An application using a property whose value is an Alt container should be aware that it can choose any one of the members of the group as appropriate.

For example, a statement about "The resolution was approved by the Rules Committee, having members Fred, Wilma, and Dino" will have the form in triples:
```
  ex:resolution exterms:approvedBy ex:rulesCommittee .
  ex:rulesCommittee rdf:type rdf:Bag .
  ex:rulesCommittee rdf:_1 ex:Fred .
  ex:rulesCommittee rdf:_2 ex:Wilma .
  ex:rulesCommittee rdf:_3 ex:Dino .
```
and this is much better than:
```
  ex:resolution exterms:approvedBy ex:Fred .
  ex:resolution exterms:approvedBy ex:Wilma .
  ex:resolution exterms:approvedBy ex:Dino .
```
since these statements say that each member individually approved the resolution.

But containers only say that certain identified resources are members; they do not say that other members do not exist. There is no way to exclude that there might be another graph somewhere that describes additional members.

For further examples [RDF Primer](http://www.w3.org/TR/REC-rdf-syntax/#containers). This content has been originally published in 2008  in [Paolo's blog](http://hcklab.blogspot.com/2008/12/moving-towards-swan-collections.html).

## RDF Collections ##
RDF provides support for describing groups containing only the specified members, in the form of RDF collections. An RDF collection is a group of things represented as a list structure in the RDF graph.

In RDF/XML a collection looks something like this:
```
<rdf:Description rdf:about="http://e.org/family/349">      
  <s:familyMembers rdf:parseType="Collection">
    <rdf:Description rdf:about="http://e.org/person/Paolo"/>
    <rdf:Description rdf:about="http://e.org/person/Emanuele"/>
    <rdf:Description rdf:about="http://e.org/person/Maria"/>
    <rdf:Description rdf:about="http://e.org/person/Franco"/>
  </s:familyMembers>
</rdf:Description>
```

This can also be written in RDF/XML by writing out the same triples (without using rdf:parseType="Collection") using the collection vocabulary:
```
<rdf:Description rdf:about="http://e.org/family/349">
   <s:familyMembers rdf:nodeID="sch1"/>
</rdf:Description>

<rdf:Description rdf:nodeID="sch1">
   <rdf:first rdf:resource="http://e.org/person/Paolo"/>
   <rdf:rest rdf:nodeID="sch2"/>
</rdf:Description>

<rdf:Description rdf:nodeID="sch2">
   <rdf:first rdf:resource="http://e.org/person/Emanuele"/>
   <rdf:rest rdf:nodeID="sch3"/>
</rdf:Description>

<rdf:Description rdf:nodeID="sch3">
   <rdf:first rdf:resource="http://e.org/person/Maria"/>
   <rdf:rest rdf:nodeID="sch4"/>
</rdf:Description>

<rdf:Description rdf:nodeID="sch4">
   <rdf:first rdf:resource="http://e.org/person/Franco"/>
   <rdf:rest rdf:resource="http://www.w3.org/1999/02/22-rdf-syntax-ns#nil"/>
</rdf:Description>
```
RDF imposes no "well-formedness" conditions on the use of the collection vocabulary (it is possible, for instance, to define multiple rdf:first elements), thus, RDF applications that require collections to be well-formed should be written to check that the collection vocabulary is being used appropriately, in order to be fully robust. Maybe OWL which can define additional constraints on the structure of RDF graphs, can rule out some of these cases?

For further examples [RDF Primer](http://www.w3.org/TR/REC-rdf-syntax/#containers). This content has been originally published in 2008  in [Paolo's blog](http://hcklab.blogspot.com/2008/12/moving-towards-swan-collections.html).