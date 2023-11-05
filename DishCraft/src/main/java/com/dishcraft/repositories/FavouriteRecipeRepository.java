package com.dishcraft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dishcraft.model.FavouriteRecipe;
import com.dishcraft.model.FavouriteRecipeId;
import com.dishcraft.model.User;

public interface FavouriteRecipeRepository extends JpaRepository<FavouriteRecipe, FavouriteRecipeId> {

	List<FavouriteRecipe> findByFavouriteRecipeIdUser(User user);
}
