package model.mapping;

import java.util.List;

public class MappingBodyDisjunction extends MappingBody{
	
	private List<MappingBody> mappingTargetExp;
	
	public MappingBodyDisjunction(List<MappingBody> mappingTargetExt) {
		this.mappingTargetExp = mappingTargetExt;
		
	}
	
	public List<MappingBody> getMappingTargetExp() {
		return mappingTargetExp;
	}


	public void setMappingTargetExp(List<MappingBody> mappingTargetExp) {
		this.mappingTargetExp = mappingTargetExp;
	}

		
}
