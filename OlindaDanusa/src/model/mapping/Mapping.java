package model.mapping;

import java.util.List;

import model.entities.Context;
import model.entities.Dataset;

import com.hp.hpl.jena.graph.Node;


public class Mapping {
	
	private Dataset mappingDataset;
	private Node mappingNode;
	private Context mappingContext;
	private List<MappingBody> mappingBodyExpressions;
	
	//mapping for predicate
	public Mapping(Dataset mappingSourceDataset, Node mappingSourceNode, Context mappingSourceContext, List<MappingBody> mappingBodyExpressions) {
		this.mappingDataset = mappingSourceDataset;
		this.mappingNode = mappingSourceNode;
		this.mappingContext = mappingSourceContext;
		this.mappingBodyExpressions = mappingBodyExpressions;
	}
	
	//mapping for Classes
	public Mapping(Dataset mappingSourceDataset, Node mappingSourceNode, List<MappingBody> mappingBodyExpressions) {
		this.mappingDataset = mappingSourceDataset;
		this.mappingNode = mappingSourceNode;
		this.mappingBodyExpressions = mappingBodyExpressions;
	}
	
	public Dataset getMappingDataset() {
		return mappingDataset;
	}

	public void setMappingDataset(Dataset mappingDataset) {
		this.mappingDataset = mappingDataset;
	}

	public Node getMappingNode() {
		return mappingNode;
	}

	public void setMappingNode(Node mappingNode) {
		this.mappingNode = mappingNode;
	}
	
	public Context getMappingContext() {
		return mappingContext;
	}

	public void setMappingContext(Context mappingContext) {
		this.mappingContext = mappingContext;
	}
	
	public void setMappingBodyExpressions(List<MappingBody> mappingBodyExpressions) {
		this.mappingBodyExpressions = mappingBodyExpressions;
	}

	public List<MappingBody> getMappingBodyExpressions() {
		return mappingBodyExpressions;
	}

	

}
