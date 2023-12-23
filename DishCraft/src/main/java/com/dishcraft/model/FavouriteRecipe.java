package com.dishcraft.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class FavouriteRecipe {
	
	@EmbeddedId
	private FavouriteRecipeId favouriteRecipeId;
	
	@Column(name = "save_date", columnDefinition = "timestamp")
	private LocalDateTime saveDate;

	private FavouriteRecipe() {}

	public FavouriteRecipe(FavouriteRecipeId favouriteRecipeId, LocalDateTime saveDate) {
		super();
		this.favouriteRecipeId = favouriteRecipeId;
		this.saveDate = saveDate;
	}

	public FavouriteRecipeId getFavouriteRecipeId() {
		return favouriteRecipeId;
	}

	public void setFavouriteRecipeId(FavouriteRecipeId favouriteRecipeId) {
		this.favouriteRecipeId = favouriteRecipeId;
	}

	public LocalDateTime getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(LocalDateTime saveDate) {
		this.saveDate = saveDate;
	}
}
