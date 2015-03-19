## Understanding Collections Ontology (CO) ##

Collections Ontology (CO) is an OWL ontology currently representing three different data structures:
  * **[Set](StructureSet.md)** implements a collection that cannot contain duplicate elements
    * Introduction to the [co:Set](StructureSet.md)
    * Set Example #1: [Defining and querying the Set of the CO contributors](SetExample1.md)
    * Set Example #2: [Collections Ontology and FOAF Group](SetsExample1AndFoafGroup.md)
  * **[Bag](StructureBag.md)** implements a collection that can have multiple copies of each element
    * Introduction to the [co:Bag](StructureBag.md)
  * **[List](StructureList.md)** (or Sequence) implements an ordered collection of elements, where the same element may occur more than once
    * Introduction to the [co:List](StructureList.md)
    * List Example #1: [Defining and querying the List of the CO contributors](ListExamplePersons.md)

Alignments with other efforts:
  * [Handling ORE proxies with CO](OREProxiesAndLists.md)
Examples:
  * [Defining the list of authors of a paper in FaBiO using CO](ListExamplePaperAuthorsFaBiO.md)

If you are interested in better understanding the reasons behind the creation of the Collections Ontology we would suggest starting reading the following pages:
  1. [RDF Containers and Collections](RDFContainersAndCollections.md)
  1. [OWL and Ordering](OWLandOrdering.md)