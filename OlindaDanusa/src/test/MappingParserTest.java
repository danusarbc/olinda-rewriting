package test;

import model.entities.Dataset;
import model.mapping.Mapping;
import model.mapping.MappingBody;
import model.mapping.MappingBodyNode;
import model.parsing.MappingParser;

public class MappingParserTest {

	public static void main(String[] args) {
		
		Dataset dataset = new Dataset("Dataset1", "http://127.0.0.1:2020/dataset1");
		
		String mappingStringProperty = "(http://swrc.ontoware.org/ontology#title, http://swrc.ontoware.org/ontology#Article, ar) <- (akt, akt:has-title, akt:Article-Reference, ar) AND (kisti, kisti:engNameOfAccomplishment, kisti:Accomplishment, ar)";
		String mappingStringClass = "(http://swrc.ontoware.org/ontology#Article) <- (akt, akt:Article-Reference) AND (kisti, kisti:Accomplishment)";
		
		MappingParser parser = new MappingParser();
		
		Mapping propertyMapping = parser.parseMappingProperty(mappingStringProperty, dataset);
		Mapping classMapping = parser.parseMappingClass(mappingStringClass, dataset);
		
		System.out.println("Property Mapping: ");
		System.out.println("Mapping node: " + propertyMapping.getMappingNode());
		System.out.println("Mapping context: " + propertyMapping.getMappingContext().getName());		
		System.out.println("Mapping body: ");
		
		for(MappingBody mappingBody : propertyMapping.getMappingBodyExpressions()){
			System.out.println(((MappingBodyNode)mappingBody).getTargetNode() + " " + ((MappingBodyNode)mappingBody).getTargetContext().getName());
		}
		
		System.out.println("\nClass Mapping: ");
		System.out.println("Mapping node: " + classMapping.getMappingNode());	
		System.out.println("Mapping body: ");
		
		for(MappingBody mappingBody : classMapping.getMappingBodyExpressions()){
			System.out.println(((MappingBodyNode)mappingBody).getTargetNode());
		}
		
		
	}

}
