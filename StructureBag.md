<table width='100%'>
<tr>
<td><a href='UnderstandingCO.md'>&lt;&lt; Understanding CO</a></td><td align='right'><b>co:Bag</b></td>
</tr>
</table>

## co:Bag ##
A **Bag** is a collection that can have multiple copies of each element. This is performed through the _co:Item_ entity. The _co:Item_ is linking exactly one resource through the relationship _co:itemContent_.

![http://collections-ontology.googlecode.com/svn/trunk/images/Collections-Ontology-Bag.png](http://collections-ontology.googlecode.com/svn/trunk/images/Collections-Ontology-Bag.png)<br />
**Figure 2: A Bag represented with Collections Ontology**

In the v2.0 of Collections Ontology, thanks to the new [OWL2](http://www.w3.org/TR/2009/PR-owl2-overview-20090922/) feature named [Property Chains](http://www.w3.org/TR/owl2-primer/#Property_Chains), it has been possible to infer automatically the belongingness to a bag, i.e., all the _co:element_ relations between a bag instance and all the other objects it effectively contains, that are de-referenced through items and the related properties _co:item_ and _co:itemContent_ for allowing repetition. This inference mechanisms is implemented through the following assertion:

```
ObjectProperty: co:element
	SubPropertyChain: co:item o co:itemContent
```