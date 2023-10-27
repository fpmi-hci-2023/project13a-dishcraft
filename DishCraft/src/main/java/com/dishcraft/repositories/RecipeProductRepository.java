package com.dishcraft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dishcraft.model.Product;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeProduct;
import com.dishcraft.model.RecipeProductId;

public interface RecipeProductRepository extends JpaRepository<RecipeProduct, RecipeProductId> {

	List<RecipeProduct> findByRecipeProductIdRecipe(Recipe recipe);
	
}