package test;

import java.util.List;

import model.File.MappingFileReader;
import model.File.QueryFileReader;
import model.File.QueryReformulation;
import model.entities.Dataset;
import model.mapping.Mapping;
import model.query.QueryGraph;

public class QueryReformulationTest {

	public static void main(String[] args) {
		
		Dataset dataset = new Dataset("Dataset1", "http://127.0.0.1:2020/dataset1");
		String queryFile = "queries/bibquery2.txt";
		QueryFileReader queryFileReader = new QueryFileReader();
		QueryGraph queryGraph = queryFileReader.getQueryFromFile(queryFile, dataset);
		
		String predicateMappingFile = "mappings/predicateMapping.txt";
		String classmappingFile = "mappings/classMapping.txt";
		MappingFileReader mappingFileReader = new MappingFileReader();
		List<Mapping> predicateMappings = mappingFileReader.getMappingsFromFileProperty(predicateMappingFile, dataset);
		List<Mapping> classMappings = mappingFileReader.getMappingsFromFileClass(classmappingFile, dataset);
		
		QueryReformulation queryReformulation = new QueryReformulation();
		List<QueryGraph> subQueriesGraph = queryReformulation.reformulateQuery(queryGraph, predicateMappings, classMappings);
		
		for(QueryGraph subQuery : subQueriesGraph){
			System.out.println("Subquery for " + subQuery.getDataset().getName() +":");
			System.out.println(queryGraph.getSelect());
			System.out.println("WHERE {");
			System.out.println(subQuery);
			System.out.println("}");
		}

	}

}
