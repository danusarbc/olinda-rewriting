package model.query;

import com.hp.hpl.jena.graph.Node;

public class QueryGraphEdge {
	
	private Node subject;
	private Node predicate;
	private Node object;
	private boolean isOptional;

	public QueryGraphEdge(Node subject, Node predicate, Node object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.isOptional = false;
	}
	
	public QueryGraphEdge(Node subject, Node predicate, Node object, boolean isOptional) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.isOptional = isOptional;
	}

	public Node getSubject() {
		return subject;
	}

	public void setSubject(Node subject) {
		this.subject = subject;
	}

	public Node getPredicate() {
		return predicate;
	}

	public void setPredicate(Node predicate) {
		this.predicate = predicate;
	}

	public Node getObject() {
		return object;
	}

	public void setObject(Node object) {
		this.object = object;
	}
	
	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	
	public String toString(){
		if(!this.isOptional()){
			return this.subject + " " + this.predicate + " " + this.object + " .";
		}
		else{
			return "OPTIONAL { " + this.subject + " " + this.subject + " " + this.object + " . }";
		}
	}
}
