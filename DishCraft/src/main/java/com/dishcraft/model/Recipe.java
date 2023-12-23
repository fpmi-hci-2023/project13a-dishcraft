package com.dishcraft.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String recipeName;
    private String description;
    
    @ManyToOne()
    @JoinColumn(name="image_id")
    private Image image;
    
    private Integer cookingTimeMinutes;
    private Integer readyTimeMinutes;
    
    @ManyToOne()
    @JoinColumn(name="complexity_id")
    private RecipeComplexity complexity;
    
    private Integer defaultPortionsNumber;
    
    @ManyToOne()
    @JoinColumn(name="author_user_id")
    private User user;
    
//    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Step> steps = new ArrayList<>();

    public Recipe(String recipeName, String description, Image image,
                  Integer cookingTimeMinutes, Integer readyTimeMinutes,
                  RecipeComplexity complexity, Integer defaultPortionsNumber, User user) {
        this.recipeName = recipeName;
        this.description = description;
        this.image = image;
        this.cookingTimeMinutes = cookingTimeMinutes;
        this.readyTimeMinutes = readyTimeMinutes;
        this.complexity = complexity;
        this.defaultPortionsNumber = defaultPortionsNumber;
        this.user = user;
    }

//    private Recipe(String recipeName, String description, Image image, Integer cookingTimeMinutes,
//			Integer readyTimeMinutes, RecipeComplexity complexity, Integer defaultPortionsNumber, User user
//			/*, List<Step> steps*/) {
//		super();
//		this.recipeName = recipeName;
//		this.description = description;
//		this.image = image;
//		this.cookingTimeMinutes = cookingTimeMinutes;
//		this.readyTimeMinutes = readyTimeMinutes;
//		this.complexity = complexity;
//		this.defaultPortionsNumber = defaultPortionsNumber;
//		this.user = user;
////		this.steps = steps;
//	}
    
	protected Recipe() {

    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

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

    public Image getImage() {
        return image;
    }

    public void setImagePath(Image image) {
        this.image = image;
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

    public RecipeComplexity getComplexity() {
        return complexity;
    }

    public void setComplexity(RecipeComplexity complexity) {
        this.complexity = complexity;
    }

    public Integer getDefaultPortionsNumber() {
        return defaultPortionsNumber;
    }

    public void setDefaultPortionsNumber(Integer defaultPortionsNumber) {
        this.defaultPortionsNumber = defaultPortionsNumber;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//	public List<Step> getSteps() {
//		return steps;
//	}
//
//	public void setSteps(List<Step> steps) {
//		this.steps = steps;
//	}

	public void setImage(Image image) {
		this.image = image;
	}
}
