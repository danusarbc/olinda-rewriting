package test;

import model.entities.Dataset;
import model.parsing.QueryGraphParser;
import model.query.QueryGraph;
import model.query.QueryGraphEdge;

import com.hp.hpl.jena.graph.Node;


public class QueryGraphParserTest {

	public static void main(String[] args) {
		
		Dataset dataset = new Dataset("DBLP", "http://dblp.rkbexplorer.com/sparql");
		
		String query =  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX akt: <http://www.aktors.org/ontology/portal#> " +
						" " +
						"SELECT ?articleTitle WHERE {  " +
						"  ?article rdf:type akt:Article-Reference . " +
						"  ?article akt:has-title ?articleTitle . " +
						"  ?article akt:has-volume ?articleVolumes . " +
						"} ";
		
		QueryGraphParser parser = new QueryGraphParser();
		QueryGraph queryGraph = parser.parseQueryGraph(query, dataset);
		
		System.out.println("Parsing result: ");
		
		for(QueryGraphEdge edge : queryGraph.getEdges()){
			Node subject = edge.getSubject();
			Node predicate = edge.getPredicate();
			Node object = edge.getObject();
			
			System.out.println(subject + " " + predicate + " " + object);
		}
		
	}

}
