package model.query;

public class QueryGraphEdgeUnion {
	
	private QueryGraphEdge edge1;
	private QueryGraphEdge edge2;
	
	public QueryGraphEdgeUnion(QueryGraphEdge edge1, QueryGraphEdge edge2) {
		this.edge1 = edge1;
		this.edge2 = edge2;
	}

	public QueryGraphEdge getEdge1() {
		return edge1;
	}

	public void setEdge1(QueryGraphEdge edge1) {
		this.edge1 = edge1;
	}

	public QueryGraphEdge getEdge2() {
		return edge2;
	}

	public void setEdge2(QueryGraphEdge edge2) {
		this.edge2 = edge2;
	}
	
}
