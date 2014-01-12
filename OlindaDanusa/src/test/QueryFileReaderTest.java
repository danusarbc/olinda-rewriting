package test;

import com.hp.hpl.jena.graph.Node;

import model.File.QueryFileReader;
import model.entities.Dataset;
import model.query.QueryGraph;
import model.query.QueryGraphEdge;

public class QueryFileReaderTest {

	public static void main(String[] args) {
		
		Dataset dataset = new Dataset("Dataset1", "http://127.0.0.1:2020/dataset1");
		String queryFile = "bibquery1.txt";
		
		QueryFileReader queryFileReader = new QueryFileReader();
		
		QueryGraph queryGraph = queryFileReader.getQueryFromFile(queryFile, dataset);
		
		for(QueryGraphEdge edge : queryGraph.getEdges()){
			Node subject = edge.getSubject();
			Node predicate = edge.getPredicate();
			Node object = edge.getObject();
			
			System.out.println(subject + " " + predicate + " " + object);
		}
	}

}
