package model.entities;

public class Dataset {
	
	private String name;
	private String sparqlEndpoint;
	
	public Dataset(String name, String sparqlEndpoint) {
		this.name = name;
		this.sparqlEndpoint = sparqlEndpoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSparqlEndpoint() {
		return sparqlEndpoint;
	}

	public void setSparqlEndpoint(String sparqlEndpoint) {
		this.sparqlEndpoint = sparqlEndpoint;
	}
	
	public boolean equals(Object dataset){
		Dataset anotherDataset = (Dataset) dataset;
		return this.name.equals(anotherDataset.name);
	}
	
	public int hashCode(){
		return 0;
	}
	
}