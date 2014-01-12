package model.mapping;

import java.util.List;

public class MappingBodyConjuction extends MappingBody{
	
	private MappingBody mappingBodyExp1;
	private MappingBody mappingBodyExp2;
	
	public MappingBodyConjuction(MappingBody mappingBodyExp1, MappingBody mappingBodyExp2){
		this.mappingBodyExp1 = mappingBodyExp1;
		this.mappingBodyExp2 = mappingBodyExp2;
		
	}
	
	public MappingBody getMappingBodyExp1() {
		return mappingBodyExp1;
	}
	public void setMappingBodyExp1(MappingBody mappingBodyExp1) {
		this.mappingBodyExp1 = mappingBodyExp1;
	}
	public MappingBody getMappingBodyExp2() {
		return mappingBodyExp2;
	}
	public void setMappingBodyExp2(MappingBody mappingBodyExp2) {
		this.mappingBodyExp2 = mappingBodyExp2;
	}
	
	
		
}
