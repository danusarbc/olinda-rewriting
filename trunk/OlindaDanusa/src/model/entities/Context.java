package model.entities;

import com.hp.hpl.jena.graph.Node;

public class Context {
	private Node name;
	private String var;
	
	public Context(Node name, String var){
		this.name = name;
		this.var = var;
	}

	public Node getName() {
		return name;
	}

	public void setName(Node name) {
		this.name = name;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	

}
