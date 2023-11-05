package com.dishcraft.payload.request;

import jakarta.validation.constraints.NotBlank;

public class RecipeProductRequest {
	@NotBlank
	private Long productId;
	
	@NotBlank
	private Float unitsAmount;
	
	@NotBlank
	private Long measureUnitId;
	
	@NotBlank
	private Integer grams;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Float getUnitsAmount() {
		return unitsAmount;
	}

	public void setUnitsAmount(Float unitsAmount) {
		this.unitsAmount = unitsAmount;
	}

	public Long getMeasureUnitId() {
		return measureUnitId;
	}

	public void setMeasureUnitId(Long measureUnitId) {
		this.measureUnitId = measureUnitId;
	}

	public Integer getGrams() {
		return grams;
	}

	public void setGrams(Integer grams) {
		this.grams = grams;
	}
	
	
}
