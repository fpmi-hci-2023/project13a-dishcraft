package com.dishcraft.payload.response;

public class NutritionalResponse {
	
	private Integer calories;
	
	private Float proteins;
	
	private Float fats;
	
	private Float carbohydrates;
	
	public NutritionalResponse(Integer calories, Float proteins, Float fats, Float carbohydrates) {
		super();
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbohydrates = carbohydrates;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Float getProteins() {
		return proteins;
	}

	public void setProteins(Float proteins) {
		this.proteins = proteins;
	}

	public Float getFats() {
		return fats;
	}

	public void setFats(Float fats) {
		this.fats = fats;
	}

	public Float getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Float carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	
}
