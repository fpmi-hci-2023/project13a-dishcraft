package com.dishcraft.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class FavouriteRecipeId implements Serializable{

	@ManyToOne()
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipe, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FavouriteRecipeId other = (FavouriteRecipeId) obj;
		return Objects.equals(recipe, other.recipe) && Objects.equals(user, other.user);
	}
}
