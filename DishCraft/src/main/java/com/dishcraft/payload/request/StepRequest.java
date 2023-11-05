package com.dishcraft.payload.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class StepRequest {
	
	@NotBlank
	private String stepDescription;
	
	@NotBlank
	private MultipartFile imageFile;
	
	@NotBlank
	private Integer numberInRecipe;

	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public Integer getNumberInRecipe() {
		return numberInRecipe;
	}

	public void setNumberInRecipe(Integer numberInRecipe) {
		this.numberInRecipe = numberInRecipe;
	}
	
	
}
