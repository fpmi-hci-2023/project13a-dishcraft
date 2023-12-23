package com.dishcraft.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RecipeProduct {
	
	@EmbeddedId
	private RecipeProductId recipeProductId;
	
	private Float unitsAmount;
	
	@ManyToOne()
	@JoinColumn(name = "unit_id")
	private MeasureUnit measureUnit;
	
	private Integer grams;
	
	private RecipeProduct() {}

	public RecipeProduct(RecipeProductId recipeProductId, Float unitAmount, MeasureUnit measureUnit, Integer grams) {
		super();
		this.recipeProductId = recipeProductId;
		this.unitsAmount = unitAmount;
		this.measureUnit = measureUnit;
		this.grams = grams;
	}

	public RecipeProductId getRecipeProductId() {
		return recipeProductId;
	}

	public void setRecipeProductId(RecipeProductId recipeProductId) {
		this.recipeProductId = recipeProductId;
	}

	public Float getUnitAmount() {
		return unitsAmount;
	}

	public void setUnitAmount(Float unitAmount) {
		this.unitsAmount = unitAmount;
	}

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Integer getGrams() {
		return grams;
	}

	public void setGrams(Integer grams) {
		this.grams = grams;
	}
	
	
}
