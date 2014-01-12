package model.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.Context;
import model.entities.Dataset;
import model.mapping.Mapping;
import model.parsing.MappingParser;



public class MappingFileReader {
	
	public List<Mapping> getMappingsFromFileProperty (String filePath, Dataset dataset){
		
		List<Mapping> mappings = new ArrayList<Mapping>();
		MappingParser mappingParser = new MappingParser();
		
		Scanner input;
		String mappingStringProperty;
		Mapping mapping;
		
		try {
			input = new Scanner(new File(filePath));
			
			while(input.hasNext()){
				mappingStringProperty = input.nextLine();
				mapping = mappingParser.parseMappingProperty(mappingStringProperty, dataset);
				
				mappings.add(mapping);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return mappings;
	}
	
public List<Mapping> getMappingsFromFileClass (String filePath, Dataset dataset){
		
		List<Mapping> mappings = new ArrayList<Mapping>();
		MappingParser mappingParser = new MappingParser();
		
		Scanner input;
		String mappingStringClass;
		Mapping mapping;
		
		try {
			input = new Scanner(new File(filePath));
			
			while(input.hasNext()){
				mappingStringClass = input.nextLine();
				mapping = mappingParser.parseMappingClass(mappingStringClass, dataset);
				
				mappings.add(mapping);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return mappings;
	}

}
