package com.dishcraft.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class RecipeProductId implements Serializable{
	
	@ManyToOne()
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@ManyToOne()
	@JoinColumn(name = "product_id")
	private Product product;
	

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(product, recipe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeProductId other = (RecipeProductId) obj;
		return Objects.equals(product, other.product) && Objects.equals(recipe, other.recipe);
	}

	
}
