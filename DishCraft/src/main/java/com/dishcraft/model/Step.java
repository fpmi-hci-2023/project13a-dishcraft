package com.dishcraft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Step {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stepId;
	
	private String stepDescription;
	
	@ManyToOne()
	@JoinColumn(name = "image_id")
	private Image image;
	
	private Integer numberInRecipe;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	private Step() {}

	public Step(String stepDescription, Image image, Integer numberInRecipe, Recipe recipe) {
		super();
		this.stepDescription = stepDescription;
		this.image = image;
		this.numberInRecipe = numberInRecipe;
		this.recipe = recipe;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Integer getNumberInRecipe() {
		return numberInRecipe;
	}

	public void setNumberInRecipe(Integer numberInRecipe) {
		this.numberInRecipe = numberInRecipe;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
