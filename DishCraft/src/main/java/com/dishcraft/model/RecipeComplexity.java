package com.dishcraft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RecipeComplexity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complexityId;
	
	private String complexityName;
	
	private RecipeComplexity() {}

	public RecipeComplexity(Integer complexityId, String complexityName) {
		super();
		this.complexityId = complexityId;
		this.complexityName = complexityName;
	}

	public Integer getComplexityId() {
		return complexityId;
	}

	public void setComplexityId(Integer complexityId) {
		this.complexityId = complexityId;
	}

	public String getComplexityName() {
		return complexityName;
	}

	public void setComplexityName(String complexityName) {
		this.complexityName = complexityName;
	}
	
	
}
