<table width='100%'>
<tr>
<td><a href='UnderstandingCO.md'>&lt;&lt; Understanding CO</a></td><td align='right'><b>List example #1</b></td>
</tr>
</table>

## Defining the List of the CO contributors ##

![http://collections-ontology.googlecode.com/svn/trunk/images/Collections%20Ontology%20-%20Simple%20list%20of%20persons.png](http://collections-ontology.googlecode.com/svn/trunk/images/Collections%20Ontology%20-%20Simple%20list%20of%20persons.png)

```
# Snippet 1

@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix co:  	 <http://purl.org/co/>.
@prefix this:    <http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl#> .

this:person rdf:type owl:Class .
this:name rdf:type owl:DatatypeProperty .

<http://purl.org/co/example/1/list/1>
    a co:List;
    co:size 3 ;
    co:firstItem :itemOne ;
    co:item :itemTwo ;
    co:lastItem :itemThree .

:itemOne 
    a co:Item ;
    co:index 1 ;
    co:itemContent <http://www.hcklab.org/foaf.rdf#me> ;
    co:nextItem :itemTwo .

:itemTwo 
    a co:Item ;
    co:index 2 ;
    co:itemContent :MarcoOcana ;
    co:nextItem :itemThree .

:itemThree 
    a co:Item ;
    co:index 3 ;
    co:itemContent :SilvioPeroni .
 		
<http://www.hcklab.org/foaf.rdf#me> 
    a this:person ;
    rdfs:label "Paolo Ciccarese" ;
    this:name "Paolo Ciccarese" .
    
:MarcoOcana
    a this:person ;
    rdfs:label "Marco Ocana" ;
    this:name "Marco Ocana" .
    
:SilvioPeroni
    a this:person ;
    rdfs:label "Silvio Peroni" ;
    this:name "Silvio Peroni" .
```

### Adding provenance and labels ###
It is possible to add labels and provenance data using the desired vocabulary. For example, the following file has been created manually by one of the contributors of the CO ontology.


```
# Snippet 2

<http://purl.org/co/example/1/list/1>
    a co:List;
    rdfs:label "Collections Ontology Contributors" ;
    dct:created "2011-10-03" ;
    dct:creator <http://www.hcklab.org/foaf.rdf#me> ;
    co:size 3 ;
    co:firstItem <http://www.hcklab.org/foaf.rdf#me> ;
    co:item :MarcoOcana ;
    co:lastItem :SilvioPeroni .

```

You can find the [COMPLETE EXAMPLE HERE](http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl).

## Examples of DL Querying ##

1) All the items that have item content (has item content) persons with name "Paolo Ciccarese":
```
item and 'has item content' some (person and (name value "Paolo Ciccarese"))
```

2) All the lists where the first person is named 'Paolo Ciccarese' (answer 'Collections Ontology Contributors'):
```
list and 'has first item' some (
     item and 'has item content' some (person and (name value "Paolo Ciccarese"))
)
```

3) All the persons lists where the first item points to a person named 'Paolo Ciccarese' and the second item points to a person named 'Silvio Peroni' (answer 'Collections Ontology Contributors'):
```
list and (
    'has first item' some (item and 'has item content' some
           (person and (name value "Paolo Ciccarese"))
    and
    'has next item' some (item and 'has item content' some
           (person and (name value "Marco Ocana"))))
)
```

4) All the persons lists where the first item points to a person named 'Paolo Ciccarese' and the last item points to a person named 'Silvio Peroni' (answer 'Collections Ontology Contributors'):
```
list and (
    'has first item' some (item and 'has item content' some
           (person and (name value "Paolo Ciccarese")))
    and
    'has last item' some (item and 'has item content' some
           (person and (name value "Silvio Peroni")))
)
```

5) All the persons lists where the last item points to a person named 'Silvio Peroni' and the previous item points to a person named 'Marco Ocana' (answer 'Collections Ontology Contributors'):
```
list and (
    'has last item' some (item and 'has item content' some
           (person and (name value "Silvio Peroni"))
    and
    'has previous item' some (item and 'has item content' some
           (person and (name value "Marco Ocana"))))
)
```

6) All the persons lists where the last item points to a person named 'Silvio Peroni' and is preceded by an item points to a person named 'Marco Ocana' (answer 'Collections Ontology Contributors'):
```
list and (
    'has last item' some (item and 'has item content' some
           (person and (name value "Silvio Peroni"))
    and
    'is preceded by' some (item and 'has item content' some
           (person and (name value "Paolo Ciccarese"))))
)
```

7) All the lists where the first person is named 'Paolo Ciccarese' and the second is 'Marco Ocana' (given the transitive nature of the property 'is followed by' the answer is 'Collections Ontology Contributors'):
```
list and (
      'has first item' some (item and
            'has item content' some (person and (name value "Paolo Ciccarese"))
            and
            'is followed by' some (item  and
                    ('has item content' some(person and(name value "Marco Ocana"))))
      )
)
```

8) All the lists containing a person named 'Paolo Ciccarese' (answer 'Collections Ontology Contributors'):
```
list and 'has item' some (
     item and 'has item content' some (person and (name value "Paolo Ciccarese"))
) 
```

9) Any list where a person named 'Paolo Ciccarese' is followed by a person named 'Silvio Peroni' (answer 'Collections Ontology Contributors'):
```
list and (
    'has item' some (item and
            'has item content' some (person and (name value "Paolo Ciccarese"))
            and
            'is followed by' some (item  and
                    ('has item content' some(person and(name value "Silvio Peroni"))))
      )
)
```

10) All the lists where a person named 'Silvio Peroni' is preceeded by a person named 'Paolo Ciccarese' (answer 'Collections Ontology Contributors'):
```
list and (
    'has item' some (item and
            'has item content' some (person and (name value "Silvio Peroni"))
            and
            'is preceded by' some (item  and
                    ('has item content' some(person and(name value "Paolo Ciccarese"))))
      )
)
```

## Examples of SPARQL Querying ##

It is possible to query the above example through SPARQL. SPARQL is particularly helpful for negations. For validating your SPARQL queries you can use the [Online SPARQL Validator](http://sws.ifi.uio.no/sparqler/validator.html) and for testing the following queries you can use [Online SPARQL processor](http://www.sparql.org/sparql.html)

11) All the lists where the first person is named 'Paolo Ciccarese' (answer 'Collections Ontology Contributors'):

```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX co: <http://purl.org/co/>
PREFIX this: <http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl#> 

SELECT ?list
  WHERE {
    ?list co:firstItem ?item .
    ?item co:itemContent ?itemContent .
    ?itemContent this:name "Paolo Ciccarese" .
  }

```


12) All the lists where the first person is named 'Paolo Ciccarese' and the last person is not 'Silvio Peroni' (answer nothing):
```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX co: <http://purl.org/co/>
PREFIX this: <http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl#> 

SELECT ?list
  WHERE {
    ?list co:firstItem ?item .
    ?item co:itemContent ?itemContent .
    ?itemContent this:name "Paolo Ciccarese" .
    FILTER NOT EXISTS {
      ?list co:lastItem ?item2 .
      ?item2 co:itemContent ?itemContent2 .
      ?itemContent2 this:name "Silvio Peroni" .
    }
  }
```

13) All the people in the list, returned according to the list order (answer 'Paolo Ciccarese', 'Marco Ocana' and 'Silvio Peroni'). This is possible when the indexes (_co:index_) have been attributed to the items.

In the current example the person named 'Paolo Ciccarese' is related to the list as content of itemOne 'firstItem' of the list and the person named 'Silvio Peroni' is related to the list as content of itemThree through 'lastItem'. As SPARQL is not performing inference it is necessary to include the three possible cases in the query (UNION):

```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX co: <http://purl.org/co/>
PREFIX this: <http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl#> 

SELECT ?person
WHERE {
  {
     ?list co:item ?item .
  } UNION {
     ?list co:firstItem ?item .
  } UNION {
     ?list co:lastItem ?item .
  }
  ?item co:itemContent ?person . 
  ?item co:index ?index .
}
ORDER BY ?index
```

Alternatively it is possible, for instance, to run the reasoner and embed the results of the inference process back in the example to be able to get the same results through:
```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX co: <http://purl.org/co/>
PREFIX this: <http://collections-ontology.googlecode.com/svn/trunk/examples/persons-list-1.ttl#> 

SELECT ?person
WHERE {
  ?list co:item ?item .
  ?item co:itemContent ?person . 
  ?item co:index ?index .
}
ORDER BY ?index
```


### References ###

If you use [Protege](http://protege.stanford.edu/), [here is a little guide for the above example](http://hcklab.blogspot.com/2011/09/collections-ontology-v2-list-of-persons.html).