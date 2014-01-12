package model.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.entities.Dataset;
import model.parsing.QueryGraphParser;
import model.query.QueryGraph;


public class QueryFileReader {
	
	public QueryGraph getQueryFromFile (String filePath, Dataset dataset){
		
		QueryGraphParser queryGraphParser = new QueryGraphParser();
		QueryGraph queryGraph = null;
		String line, queryString = "";
		
		try {
			Scanner input = new Scanner(new File(filePath));
			
			while(input.hasNext()){
				line = input.nextLine();
				queryString += line + "\n";
			}
			
			queryGraph = queryGraphParser.parseQueryGraph(queryString, dataset);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return queryGraph;
	}

}
