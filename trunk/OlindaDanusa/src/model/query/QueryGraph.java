package model.query;

import java.util.ArrayList;
import java.util.List;

import model.entities.Dataset;


public class QueryGraph {

	private Dataset dataset;
	private List<QueryGraphEdge> edges;
	private List<QueryGraphEdgeUnion> edgeUnions;
	private List<QueryGraphFilter> filters;
	private String select;

	public QueryGraph(Dataset dataset, List<QueryGraphEdge> edges) {
		this.dataset = dataset;
		this.edges = edges;
		this.edgeUnions = new ArrayList<QueryGraphEdgeUnion>();
		this.filters = new ArrayList<QueryGraphFilter>();
	}

	public QueryGraph(Dataset dataset, List<QueryGraphEdge> edges,
			List<QueryGraphEdgeUnion> unionEdges) {
		this.dataset = dataset;
		this.edges = edges;
		this.edgeUnions = unionEdges;
		this.filters = new ArrayList<QueryGraphFilter>();
	}
	
	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public List<QueryGraphEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<QueryGraphEdge> edges) {
		this.edges = edges;
	}	

	public List<QueryGraphEdgeUnion> getUnionEdges() {
		return edgeUnions;
	}

	public void setUnionEdges(List<QueryGraphEdgeUnion> unionEdges) {
		this.edgeUnions = unionEdges;
	}

	public List<QueryGraphFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<QueryGraphFilter> filters) {
		this.filters = filters;
	}
	
	public String toString(){

		String queryGraphString = "";

		for (QueryGraphEdge edge: edges){
			queryGraphString += edge + "\n";
			
		}

		for (QueryGraphEdgeUnion unionEdge : edgeUnions){

			QueryGraphEdge edge1 = unionEdge.getEdge1();
			QueryGraphEdge edge2 = unionEdge.getEdge2();

			queryGraphString += "{ \n";
			queryGraphString += "\t { " + edge1 + " } ";
			queryGraphString += "UNION ";
			queryGraphString += "{ " + edge2 + " } ";
			queryGraphString += "\n} \n";

		}
		
		for (QueryGraphFilter filter : filters){
			queryGraphString += "FILTER " + filter.getExpression() + " \n";
		}

		return queryGraphString;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
}
