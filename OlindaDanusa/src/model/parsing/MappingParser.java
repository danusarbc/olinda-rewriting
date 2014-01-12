package model.parsing;

import java.util.ArrayList;
import java.util.List;

import model.entities.Context;
import model.entities.Dataset;
import model.mapping.Mapping;
import model.mapping.MappingBody;
import model.mapping.MappingBodyConjuction;
import model.mapping.MappingBodyNode;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.util.NodeFactory;


public class MappingParser {

	public Mapping parseMappingClass (String mappingString, Dataset dataset){

		String[] mappingParts = mappingString.split(" <- ");
		String headNodeString = mappingParts[0];//head
		String[] bodyExpressionString = splitExpressions(mappingParts[1]);//body

		//head string
		headNodeString = headNodeString.substring(1, headNodeString.length()-1);
		Node headNode = NodeFactory.parseNode("<" + headNodeString + ">");

		// body expressions
		List<MappingBody> bodyExpressions = new ArrayList<MappingBody>();

		for (int i = 0; i < bodyExpressionString.length; i++){

			String targetExpressionString = bodyExpressionString[i];
			MappingBody mappingTarget = parseBodyExpression(targetExpressionString, false);

			bodyExpressions.add(mappingTarget);
		}

		Mapping mapping = new Mapping(dataset, headNode, bodyExpressions);

		return mapping;
	}
	
	public Mapping parseMappingProperty (String mappingString, Dataset dataset){

		String[] mappingParts = mappingString.split(" <- ");
		String headNodeString = mappingParts[0];
		String[] bodyExpressionsString = splitExpressions(mappingParts[1]);

		//head
		headNodeString = headNodeString.substring(1, headNodeString.length()-1);
		String[] headParts = headNodeString.split(", ");
		
		//part 1 of head 
		Node headNode = NodeFactory.parseNode("<" + headParts[0] + ">");
		
		//part 2 of head	
		Node headContextNode = NodeFactory.parseNode("<" +  headParts[1] + ">");
		
		//part 3 of head
		String headPartNodeString2 = headParts[2];
		
		Context contextHead = new Context(headContextNode, headPartNodeString2);
		
		// body expressions
		List<MappingBody> bodyExpressions = new ArrayList<MappingBody>();

		for (int i = 0; i < bodyExpressionsString.length; i++){

			String bodyExpressionString = bodyExpressionsString[i];
			MappingBody mappingBody = parseBodyExpression(bodyExpressionString, true);
			bodyExpressions.add(mappingBody);

		}

		Mapping mapping = new Mapping(dataset, headNode, contextHead, bodyExpressions);

		return mapping;
	}

private MappingBody parseBodyExpression (String bodyExpressionString, boolean isPropertyMapping){

		MappingBody mappingBody = null;
		char firstCharacter = bodyExpressionString.charAt(0);

		// mapping body node...first character
		if(firstCharacter == '('){ 
			bodyExpressionString = bodyExpressionString.substring(1, bodyExpressionString.length()-1);
			
			String[] bodyExpressionParts = bodyExpressionString.split(", ");
			
			//part 1 of body
			Dataset bodyDataset = new Dataset(bodyExpressionParts[0], "");
			
			//part 2 of body
			Node bodytNode = NodeFactory.parseNode("<" + bodyExpressionParts[1] + ">");
			
			Context contextBody = null;
			
			if(isPropertyMapping){ // it has a context, otherwise, if it is a class mapping, it does not have a context
				//part 3 of body
				Node bodyContextNode = NodeFactory.parseNode("<" + bodyExpressionParts[2] + ">");
				
				//part 4 of body
				String bodyVarNode = bodyExpressionParts[3];
			
				contextBody = new Context(bodyContextNode, bodyVarNode);
			}
			
			mappingBody = new MappingBodyNode(bodyDataset, bodytNode, contextBody);

		}
		else if(firstCharacter == '['){ // mapping body conjuction
			bodyExpressionString = bodyExpressionString.substring(1, bodyExpressionString.length()-1);

			// looking for logical operator
			int indexOperator = bodyExpressionString.indexOf(')')+2;
			String operator = bodyExpressionString.substring(indexOperator, indexOperator+3);

			String targetExp1String = bodyExpressionString.substring(0,indexOperator-1);
			String targetExp2String = bodyExpressionString.substring(indexOperator+3);

			if(operator.equals("AND")){
				MappingBody mappingTargetExp1 = parseBodyExpression(targetExp1String, isPropertyMapping);
				MappingBody mappingTargetExp2 = parseBodyExpression(targetExp2String.substring(1), isPropertyMapping);

				mappingBody = new MappingBodyConjuction(mappingTargetExp1, mappingTargetExp2);

			}
			/*else if(operator.equals("OR ")){
				MappingBody mappingTargetExp1 = parseBodyExpression(targetExp1String);
				MappingBody mappingTargetExp2 = parseBodyExpression(targetExp2String);

				mappingBody = new MappingBodyDisjunction(mappingTargetExp1, mappingTargetExp2);
			}*/
		}

		return mappingBody;
	}

	private String[] splitExpressions(String bodyExpressions) {

		List<String> splitExpressions = new ArrayList<String>();
		String newExpression = "";
		boolean inBrackets = false;
		boolean inParenthesis = false;

		for (int i = 0; i < bodyExpressions.length(); i++){

			char character = bodyExpressions.charAt(i);

			if(character == '['){
				inBrackets = true;
			} else if(character == ']'){
				newExpression += character;
				splitExpressions.add(newExpression);
				newExpression = "";
				inBrackets= false;
			}

			if(!inBrackets){

				if(character == '('){
					inParenthesis = true;
				}
				else if(character == ')'){
					newExpression += character;
					splitExpressions.add(newExpression);
					newExpression = "";
					inParenthesis = false;
				}
			}

			if(inParenthesis || inBrackets){
				newExpression += character;
			}

		}

		return splitExpressions.toArray(new String[]{});
	}

}
