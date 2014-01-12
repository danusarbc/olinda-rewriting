package model.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.entities.Dataset;
import model.mapping.*;
import model.query.*;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.util.NodeFactory;


public class QueryReformulation {

	public List<QueryGraph> reformulateQuery (QueryGraph queryGraph, 
			List<Mapping> predicateMappings, 
			List<Mapping> classMappings){

		HashMap<String, QueryGraph> subQueriesGraph = new HashMap<String, QueryGraph>();
		QueryGraph subQueryGraph;
		List<QueryGraphEdge> subQueryEdges;
		List<QueryGraphEdgeUnion> subQueryEdgeUnions;
		List<MappingBody> bodyExpressions;
		Dataset targetDataset;
		int nextTempVariable = 1;

		for (QueryGraphEdge edge : queryGraph.getEdges()){
			Node subject = edge.getSubject();
			Node predicate = edge.getPredicate();
			Node object = edge.getObject();
			boolean isOptional = edge.isOptional();

			// generating new edges according to predicate mappings
			for (Mapping predicateMapping : predicateMappings){

				Node headContext = predicateMapping.getMappingContext().getName();
				
				if (predicate.isURI() && predicate.getURI().equals(predicateMapping.getMappingNode().getURI())){
					bodyExpressions = predicateMapping.getMappingBodyExpressions();

					for(MappingBody bodyExpression : bodyExpressions){

						if (bodyExpression instanceof MappingBodyNode){
							MappingBodyNode targetNode = (MappingBodyNode) bodyExpression;

							// checking if the contexts are the same
							Node predicateContext = getPredicateContext(subject, queryGraph.getEdges());
							
							if(checkContext(predicateContext, headContext, targetNode.getTargetContext().getName(), classMappings)){

								targetDataset = targetNode.getTargetDataset();
								
								Node newPredicate = targetNode.getTargetNode();

								QueryGraphEdge newEdge = new QueryGraphEdge(subject, newPredicate, object, isOptional);

								subQueryGraph = subQueriesGraph.get(targetDataset.getName());

								if (subQueryGraph == null){
									subQueryEdges = new ArrayList<QueryGraphEdge>();
									subQueryEdges.add(newEdge);

									subQueryGraph = new QueryGraph(targetDataset, subQueryEdges);								
								}
								else{
									subQueryEdges = subQueryGraph.getEdges();
									subQueryEdges.add(newEdge);

									subQueryGraph.setEdges(subQueryEdges);
								}

								subQueriesGraph.put(targetDataset.getName(), subQueryGraph);

							}

						}
						else if (bodyExpression instanceof MappingBodyConjuction){

							MappingBodyConjuction bodyConjunction = (MappingBodyConjuction) bodyExpression;
							MappingBodyNode bodyNode1 = (MappingBodyNode) bodyConjunction.getMappingBodyExp1();
							MappingBodyNode bodyNode2 = (MappingBodyNode) bodyConjunction.getMappingBodyExp2();
							
							// checking if at least one of the target context are the same as the head context
							
							Node predicateContext = getPredicateContext(subject, queryGraph.getEdges());
							
							if(checkContext(predicateContext, headContext, bodyNode1.getTargetContext().getName(), classMappings) || 
							   checkContext(predicateContext, headContext, bodyNode2.getTargetContext().getName(), classMappings)){

								targetDataset = bodyNode1.getTargetDataset();

								Node newPredicate1 = bodyNode1.getTargetNode();
								Node newPredicate2 = bodyNode2.getTargetNode();
								Node tempNode = NodeFactory.parseNode("?t" + nextTempVariable);
								nextTempVariable++;

								QueryGraphEdge newEdge1 = new QueryGraphEdge(subject, newPredicate2, tempNode, isOptional);
								QueryGraphEdge newEdge2 = new QueryGraphEdge(tempNode, newPredicate1, object, isOptional);

								subQueryGraph = subQueriesGraph.get(targetDataset.getName());

								if (subQueryGraph == null){
									subQueryEdges = new ArrayList<QueryGraphEdge>();
									subQueryEdges.add(newEdge1);
									subQueryEdges.add(newEdge2);

									subQueryGraph = new QueryGraph(targetDataset, subQueryEdges);								
								}
								else{
									subQueryEdges = subQueryGraph.getEdges();
									subQueryEdges.add(newEdge1);
									subQueryEdges.add(newEdge2);

									subQueryGraph.setEdges(subQueryEdges);
								}

								subQueriesGraph.put(targetDataset.getName(), subQueryGraph);
							}

						}
						/*else if (bodyExpression instanceof MappingBodyDisjunction){

							MappingBodyDisjunction targetDisjunction = (MappingBodyDisjunction) bodyExpression;
							MappingBodyNode targetNode1 = (MappingBodyNode) targetDisjunction.getMappingTargetExp1();
							MappingBodyNode targetNode2 = (MappingBodyNode) targetDisjunction.getMappingTargetExp2();
							targetDataset = targetNode1.getTargetDataset();

							Node newPredicate1 = targetNode1.getTargetNode();
							Node newPredicate2 = targetNode2.getTargetNode();

							QueryGraphEdge newEdge1 = new QueryGraphEdge(subject, newPredicate1, object, isOptional);
							QueryGraphEdge newEdge2 = new QueryGraphEdge(subject, newPredicate2, object, isOptional);
							QueryGraphEdgeUnion newEdgeUnion = new QueryGraphEdgeUnion(newEdge1, newEdge2);

							subQueryGraph = subQueriesGraph.get(targetDataset.getName());

							if (subQueryGraph == null){
								subQueryEdgeUnions = new ArrayList<QueryGraphEdgeUnion>();
								subQueryEdgeUnions.add(newEdgeUnion);

								subQueryGraph = new QueryGraph(targetDataset, new ArrayList<QueryGraphEdge>(), subQueryEdgeUnions);								
							}
							else{
								subQueryEdges = subQueryGraph.getEdges();
								subQueryEdgeUnions = subQueryGraph.getUnionEdges();
								subQueryEdgeUnions.add(newEdgeUnion);

								subQueryGraph = new QueryGraph(targetDataset, subQueryEdges, subQueryEdgeUnions);								
							}

							subQueriesGraph.put(targetDataset.getName(), subQueryGraph);

						}*/						
					}					
				}
			}

			// generating new edges according to class mappings
			for (Mapping classMapping : classMappings){
				if(object.isURI() && object.getURI().equals(classMapping.getMappingNode().getURI())){

					bodyExpressions = classMapping.getMappingBodyExpressions();

					for(MappingBody targetExpression : bodyExpressions){

						if (targetExpression instanceof MappingBodyNode){
							MappingBodyNode targetNode = (MappingBodyNode) targetExpression;
							targetDataset = targetNode.getTargetDataset();
							
							Node newObject = targetNode.getTargetNode();

							QueryGraphEdge newEdge = new QueryGraphEdge(subject, predicate, newObject, isOptional);

							subQueryGraph = subQueriesGraph.get(targetDataset.getName());

							if (subQueryGraph == null){
								subQueryEdges = new ArrayList<QueryGraphEdge>();
								subQueryEdges.add(newEdge);

								subQueryGraph = new QueryGraph(targetDataset, subQueryEdges);								
							}
							else{
								subQueryEdges = subQueryGraph.getEdges();
								subQueryEdges.add(newEdge);

								subQueryGraph.setEdges(subQueryEdges);
							}

							subQueriesGraph.put(targetDataset.getName(), subQueryGraph);

						}
						else if (targetExpression instanceof MappingBodyConjuction){

							MappingBodyConjuction bodyConjunction = (MappingBodyConjuction) targetExpression;
							MappingBodyNode bodyNode1 = (MappingBodyNode) bodyConjunction.getMappingBodyExp1();
							MappingBodyNode bodyNode2 = (MappingBodyNode) bodyConjunction.getMappingBodyExp2();
							targetDataset = bodyNode1.getTargetDataset();

							Node newObject1 = bodyNode1.getTargetNode();
							Node newObject2 = bodyNode2.getTargetNode();

							QueryGraphEdge newEdge1 = new QueryGraphEdge(subject, predicate, newObject1, isOptional);
							QueryGraphEdge newEdge2 = new QueryGraphEdge(subject, predicate, newObject2, isOptional);

							subQueryGraph = subQueriesGraph.get(targetDataset.getName());

							if (subQueryGraph == null){
								subQueryEdges = new ArrayList<QueryGraphEdge>();
								subQueryEdges.add(newEdge1);
								subQueryEdges.add(newEdge2);

								subQueryGraph = new QueryGraph(targetDataset, subQueryEdges);								
							}
							else{
								subQueryEdges = subQueryGraph.getEdges();
								subQueryEdges.add(newEdge1);
								subQueryEdges.add(newEdge2);

								subQueryGraph.setEdges(subQueryEdges);
							}

							subQueriesGraph.put(targetDataset.getName(), subQueryGraph);

						}/*
						else if (targetExpression instanceof MappingBodyDisjunction){

							MappingBodyDisjunction targetDisjunction = (MappingBodyDisjunction) targetExpression;
							MappingBodyNode targetNode1 = (MappingBodyNode) targetDisjunction.getMappingTargetExp1();
							MappingBodyNode targetNode2 = (MappingBodyNode) targetDisjunction.getMappingTargetExp2();
							targetDataset = targetNode1.getTargetDataset();

							Node newObject1 = targetNode1.getTargetNode();
							Node newObject2 = targetNode2.getTargetNode();

							QueryGraphEdge newEdge1 = new QueryGraphEdge(subject, predicate, newObject1, isOptional);
							QueryGraphEdge newEdge2 = new QueryGraphEdge(subject, predicate, newObject2, isOptional);
							QueryGraphEdgeUnion newEdgeUnion = new QueryGraphEdgeUnion(newEdge1, newEdge2);

							subQueryGraph = subQueriesGraph.get(targetDataset.getName());

							if (subQueryGraph == null){
								subQueryEdgeUnions = new ArrayList<QueryGraphEdgeUnion>();
								subQueryEdgeUnions.add(newEdgeUnion);

								subQueryGraph = new QueryGraph(targetDataset, new ArrayList<QueryGraphEdge>(), subQueryEdgeUnions);								
							}
							else{
								subQueryEdges = subQueryGraph.getEdges();
								subQueryEdgeUnions = subQueryGraph.getUnionEdges();
								subQueryEdgeUnions.add(newEdgeUnion);

								subQueryGraph = new QueryGraph(targetDataset, subQueryEdges, subQueryEdgeUnions);								
							}

							subQueriesGraph.put(targetDataset.getName(), subQueryGraph);

						}*/						
					}		
				}
			}
			
			// handling the case when both the predicate and object are variables, in this case, the triple pattern is generated for all the
			// datasets
			if(predicate.isVariable() && object.isVariable()){
				QueryGraphEdge newEdge = new QueryGraphEdge(subject, predicate, object, isOptional);
				
				Set<Dataset> datasets = new HashSet<Dataset>();
				
				// getting all the datasets which appear into a predicate mapping
				for(Mapping predicateMapping : predicateMappings){
					for(MappingBody bodyExpression : predicateMapping.getMappingBodyExpressions()){
						
						if(bodyExpression instanceof MappingBodyNode){
							MappingBodyNode bodyNode = (MappingBodyNode) bodyExpression;
							datasets.add(bodyNode.getTargetDataset());
						}
						
						if(bodyExpression instanceof MappingBodyConjuction){
							MappingBodyConjuction bodyConjunction = (MappingBodyConjuction) bodyExpression;
							MappingBodyNode bodyNode1 = (MappingBodyNode) bodyConjunction.getMappingBodyExp1();
							
							datasets.add(bodyNode1.getTargetDataset());
						}
						
					}
				}
				
				// getting all the datasets which appear into a class mapping
				for(Mapping classMapping : classMappings){
					for(MappingBody bodyExpression : classMapping.getMappingBodyExpressions()){
						
						if(bodyExpression instanceof MappingBodyNode){
							MappingBodyNode bodyNode = (MappingBodyNode) bodyExpression;
							datasets.add(bodyNode.getTargetDataset());
						}
						
						if(bodyExpression instanceof MappingBodyConjuction){
							MappingBodyConjuction bodyConjunction = (MappingBodyConjuction) bodyExpression;
							MappingBodyNode bodyNode1 = (MappingBodyNode) bodyConjunction.getMappingBodyExp1();
							
							datasets.add(bodyNode1.getTargetDataset());
						}
						
					}
				}
				
				Iterator<Dataset> iteratorDatasets = datasets.iterator();
				
				// generating the same edge for every dataset
				while(iteratorDatasets.hasNext()){
					
					targetDataset = iteratorDatasets.next();
					
					subQueryGraph = subQueriesGraph.get(targetDataset.getName());

					if (subQueryGraph == null){
						subQueryEdges = new ArrayList<QueryGraphEdge>();
						subQueryEdges.add(newEdge);

						subQueryGraph = new QueryGraph(targetDataset, subQueryEdges);								
					}
					else{
						subQueryEdges = subQueryGraph.getEdges();
						subQueryEdges.add(newEdge);

						subQueryGraph.setEdges(subQueryEdges);
					}					
					subQueriesGraph.put(targetDataset.getName(), subQueryGraph);					
				}
			}
		}

		List<QueryGraph> subQueriesList = new ArrayList<QueryGraph>(subQueriesGraph.values());

		//generating the query filters for each subquery:
		List<QueryGraphFilter> filters = queryGraph.getFilters();

		for(QueryGraphFilter filter : filters){
			Node filterVar = filter.getVar();
			for(QueryGraph subQuery : subQueriesList){

				if(subQuery.toString().contains(" " + filterVar.toString() + " ")){
					QueryGraphFilter subQueryFilter = new QueryGraphFilter(filter.getExpression(), filterVar);
					subQuery.getFilters().add(subQueryFilter);
				}				
			}

		}

		return subQueriesList;
	}
	
	private Node getPredicateContext(Node subject, List<QueryGraphEdge> queryGraphEdges) {
		
		// getting the context for the corresponding predicate by using the rdf:type
		
		Node predicateContext = null;
		
		for(QueryGraphEdge graphEdge: queryGraphEdges){
			if(graphEdge.getPredicate().toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				predicateContext = graphEdge.getObject();
				break;
			}
		}
		
		return predicateContext;
		
	}

	private boolean checkContext(Node predicateContext, Node headContext, Node bodyContext, List<Mapping> classMappings){
		
		// using the class mappings for checking if the head context is the same as body context
		
		boolean sameContext = false;
		
		for(Mapping mapping : classMappings){
			if(mapping.getMappingNode().equals(headContext)){
				
				for(MappingBody bodyExpression : mapping.getMappingBodyExpressions()){
					MappingBodyNode bodyNode = (MappingBodyNode) bodyExpression;
					
					if(bodyNode.getTargetNode().equals(bodyContext)){
						
						if(predicateContext != null){ // if exists the context for predicate, we must verify it
							if(predicateContext.equals(headContext)){
								sameContext = true;
								break;
							}
						}
						else{
							sameContext = true;
							break;
						}
						
					}
				}
			}
		}
		
		return sameContext;
		
	}

}