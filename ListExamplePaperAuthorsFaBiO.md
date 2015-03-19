## Modelling authors of a paper ##
A simple representation of the authors of a conference paper using the <a href='http://purl.org/spar/fabio' title='FRBR-aligned Bibliographic Ontology'>FaBiO</a> and <a href='http://xmlns.com/foaf/0.1' title='Friend Of A Friend'>FOAF</a>.

```
# Snippet 1

@prefix rdf: 		<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix dcterms: 	<http://purl.org/dc/terms/> .
@prefix fabio: 		<http://purl.org/spar/fabio/> .
@prefix foaf:		<http://xmlns.com/foaf/0.1/>.

<http://dblp.uni-trier.de/rec/bibtex/conf/owled/DrummondRSMHWS06> a fabio:ConferencePaper
	; dcterms:title "Putting OWL in Order: Patterns for Sequences in OWL"
	; dcterms:creator :a1 , :a2 , :a3 , :a4 , :a5 , :a6 , :a7 .
 
:a1 a foaf:Person ; foaf:givenName "Nick" ; foaf:familyName "Drummond" .
:a2 a foaf:Person ; foaf:givenName "Alan" ; foaf:familyName "Rector" .
:a3 a foaf:Person ; foaf:givenName "Robert" ; foaf:familyName "Stevens" .
:a4 a foaf:Person ; foaf:givenName "Georgina" ; foaf:familyName "Moulton" .
:a5 a foaf:Person ; foaf:givenName "Matthew" ; foaf:familyName "Horridge" .
:a6 a foaf:Person ; foaf:givenName "Hai H." ; foaf:familyName "Wang" .
:a7 a foaf:Person ; foaf:givenName "Julian" ; foaf:familyName "Seidenberg" .
```

### Expressing order among authors ###
How can I specify the order among authors as they appears on the paper head? With CO, it is possible to create a list to put people, that are authors of the paper, in a specific order.

```
# Snippet 2

@prefix rdf: 		<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix dcterms: 	<http://purl.org/dc/terms/> .
@prefix fabio: 		<http://purl.org/spar/fabio/> .
@prefix foaf:		<http://xmlns.com/foaf/0.1/> .
@prefix co:		<http://purl.org/co/> .

<http://dblp.uni-trier.de/rec/bibtex/conf/owled/DrummondRSMHWS06> a fabio:ConferencePaper
	; dcterms:title "Putting OWL in Order: Patterns for Sequences in OWL"
	; dcterms:creator :a1 , :a2 , :a3 , :a4 , :a5 , :a6 , :a7 .
 
:a1 a foaf:Person ; foaf:givenName "Nick" ; foaf:familyName "Drummond" .
:a2 a foaf:Person ; foaf:givenName "Alan" ; foaf:familyName "Rector" .
:a3 a foaf:Person ; foaf:givenName "Robert" ; foaf:familyName "Stevens" .
:a4 a foaf:Person ; foaf:givenName "Georgina" ; foaf:familyName "Moulton" .
:a5 a foaf:Person ; foaf:givenName "Matthew" ; foaf:familyName "Horridge" .
:a6 a foaf:Person ; foaf:givenName "Hai H." ; foaf:familyName "Wang" .
:a7 a foaf:Person ; foaf:givenName "Julian" ; foaf:familyName "Seidenberg" .

[] a co:List
	; co:firstItem :i1
	; co:item :i2 , :i3 , :i4 , :i5 , :i6
	; co:lastItem :i7 .

:i1 a co:ListItem ; co:itemContent :a1 ; co:nextItem :i2 .
:i2 a co:ListItem ; co:itemContent :a2 ; co:nextItem :i3 .
:i3 a co:ListItem ; co:itemContent :a3 ; co:nextItem :i4 .
:i4 a co:ListItem ; co:itemContent :a4 ; co:nextItem :i5 .
:i5 a co:ListItem ; co:itemContent :a5 ; co:nextItem :i6 .
:i6 a co:ListItem ; co:itemContent :a6 ; co:nextItem :i7 .
:i7 a co:ListItem ; co:itemContent :a7 .
```

### Authors as list of people ###
The previous example, in which it is shown how to create ordered list of people that are, at the same time, authors of a particular paper, kept the list of people and the roles these people have completely de-contextualized. In fact, on the one hand, we described some people having the function of being the authors of a paper; on the other hand, we ordered those people in a certain way, reflecting how they are presented within the paper head (i.e., when they hold the role of being author).

In this way, we did not define any link between the function of being an author and the position of a person within the paper-related list of authors. The following example shows how to create this link.

```
# Snippet 3

@prefix rdf: 		<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs:    	<http://www.w3.org/2000/01/rdf-schema#> .
@prefix owl:		<http://www.w3.org/2002/07/owl#> .
@prefix dcterms: 	<http://purl.org/dc/terms/> .
@prefix fabio: 		<http://purl.org/spar/fabio/> .
@prefix foaf:		<http://xmlns.com/foaf/0.1/> .
@prefix co:		<http://purl.org/co/> .

:ListOfPeople owl:equivalentClass [ a owl:Class 
	; owl:intersectionOf ( 
		co:List
		[ a owl:Restriction 
			; owl:onProperty co:item 
			; owl:allValuesFrom [ a owl:Restriction 
				; owl:onProperty co:itemContent 
				; owl:allValuesFrom foaf:Person ] ] ) ] .

<http://dblp.uni-trier.de/rec/bibtex/conf/owled/DrummondRSMHWS06> a fabio:ConferencePaper
	; dcterms:title "Putting OWL in Order: Patterns for Sequences in OWL"
	; dcterms:creator :authors .
 
:authors a co:ListOfPeople
	; co:firstItem :i1
	; co:item :i2 , :i3 , :i4 , :i5 , :i6
	; co:lastItem :i7 .

:i1 a co:ListItem ; co:itemContent :a1 ; co:nextItem :i2 .
:i2 a co:ListItem ; co:itemContent :a2 ; co:nextItem :i3 .
:i3 a co:ListItem ; co:itemContent :a3 ; co:nextItem :i4 .
:i4 a co:ListItem ; co:itemContent :a4 ; co:nextItem :i5 .
:i5 a co:ListItem ; co:itemContent :a5 ; co:nextItem :i6 .
:i6 a co:ListItem ; co:itemContent :a6 ; co:nextItem :i7 .
:i7 a co:ListItem ; co:itemContent :a7 .
 
:a1 a foaf:Person ; foaf:givenName "Nick" ; foaf:familyName "Drummond" .
:a2 a foaf:Person ; foaf:givenName "Alan" ; foaf:familyName "Rector" .
:a3 a foaf:Person ; foaf:givenName "Robert" ; foaf:familyName "Stevens" .
:a4 a foaf:Person ; foaf:givenName "Georgina" ; foaf:familyName "Moulton" .
:a5 a foaf:Person ; foaf:givenName "Matthew" ; foaf:familyName "Horridge" .
:a6 a foaf:Person ; foaf:givenName "Hai H." ; foaf:familyName "Wang" .
:a7 a foaf:Person ; foaf:givenName "Julian" ; foaf:familyName "Seidenberg" .
```

### Leveraging inference ###
By means of CO inference capabilities, it is possible to make a list in a less verbose way, leaving to infer all the others list-to-item and item-to-item links to a reasoner. In fact, the list presented in the previous example can be re-written as follows:

```
:authors a co:ListOfPeople
	; co:firstItem [ co:itemContent :a1
	; co:nextItem  [ co:itemContent :a2
	; co:nextItem  [ co:itemContent :a3
	; co:nextItem  [ co:itemContent :a4
	; co:nextItem  [ co:itemContent :a5
	; co:nextItem  [ co:itemContent :a6
	; co:nextItem  [ co:itemContent :a7 ] ] ] ] ] ] ] .
```