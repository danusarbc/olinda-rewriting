package model.mapping;

import model.entities.Context;
import model.entities.Dataset;

import com.hp.hpl.jena.graph.Node;


public class MappingBodyNode extends MappingBody{
	
	private Dataset targetDataset;
	private Node targetNode;
	private Context targetContext;
	
	public MappingBodyNode(Dataset targetDataset, Node targetNode, Context tarContext) {
		this.targetDataset = targetDataset;
		this.targetNode = targetNode;
		this.targetContext = tarContext;
	}

	public Dataset getTargetDataset() {
		return targetDataset;
	}

	public void setTargetDataset(Dataset targetDataset) {
		this.targetDataset = targetDataset;
	}

	public Node getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}
	
	public Context getTargetContext() {
		return targetContext;
	}

	public void setTargetContext(Context targetContext) {
		this.targetContext = targetContext;
	}
	
}
