package com.dishcraft.payload.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class RecipeRequest {

	@NotBlank
	private String recipeName;
	
	@NotBlank
    private String description;
	
	@NotBlank
    private MultipartFile imageFile;

	@NotBlank
    private Integer cookingTimeMinutes;
    
    @NotBlank
    private Integer readyTimeMinutes;
    
    @NotBlank
    private Integer complexityId;
    
    @NotBlank
    private Integer defaultPortionsNumber;
    
    public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public Integer getCookingTimeMinutes() {
		return cookingTimeMinutes;
	}

	public void setCookingTimeMinutes(Integer cookingTimeMinutes) {
		this.cookingTimeMinutes = cookingTimeMinutes;
	}

	public Integer getReadyTimeMinutes() {
		return readyTimeMinutes;
	}

	public void setReadyTimeMinutes(Integer readyTimeMinutes) {
		this.readyTimeMinutes = readyTimeMinutes;
	}

	public Integer getComplexityId() {
		return complexityId;
	}

	public void setComplexityId(Integer complexityId) {
		this.complexityId = complexityId;
	}

	public Integer getDefaultPortionsNumber() {
		return defaultPortionsNumber;
	}

	public void setDefaultPortionsNumber(Integer defaultPortionsNumber) {
		this.defaultPortionsNumber = defaultPortionsNumber;
	}
}
