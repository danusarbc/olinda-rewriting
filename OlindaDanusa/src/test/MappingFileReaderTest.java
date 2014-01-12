package test;

import java.util.List;

import model.File.MappingFileReader;
import model.entities.Dataset;
import model.mapping.Mapping;

public class MappingFileReaderTest {

	public static void main(String[] args) {
		
		Dataset dataset = new Dataset("Dataset1", "http://127.0.0.1:2020/dataset1");
		
		String predicateMappingFile = "predicateMapping.txt";
		String classmappingFile = "classMapping.txt";
		
		MappingFileReader mappingFileReader = new MappingFileReader();
		
		
		List<Mapping> predicateMapping = mappingFileReader.getMappingsFromFileProperty(predicateMappingFile, dataset);
		List<Mapping> classMapping = mappingFileReader.getMappingsFromFileClass(classmappingFile, dataset);
		
		System.out.println(predicateMapping.size());
		System.out.println(classMapping.size());
	}

}
