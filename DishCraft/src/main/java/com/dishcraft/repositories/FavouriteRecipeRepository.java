package com.dishcraft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dishcraft.model.FavouriteRecipe;
import com.dishcraft.model.FavouriteRecipeId;

public interface FavouriteRecipeRepository extends JpaRepository<FavouriteRecipe, FavouriteRecipeId> {

}
