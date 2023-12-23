package com.dishcraft.services;

import com.dishcraft.model.FavouriteRecipe;
import com.dishcraft.model.FavouriteRecipeId;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repositories.FavouriteRecipeRepository;
import com.dishcraft.repositories.RecipeRepository;
import com.dishcraft.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FavouriteRecipeRepository favouriteRecipeRepository;

    public UserService(UserRepository userRepository, RecipeRepository recipeRepository, FavouriteRecipeRepository favouriteRecipeRepository) {
        this.userRepository = userRepository;
		this.recipeRepository = recipeRepository;
		this.favouriteRecipeRepository = favouriteRecipeRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public void deleteUserById(Long id) {
    	userRepository.deleteById(id);
    }
    
    public List<Recipe> getAllRecipesByUser(User user) {
    	return recipeRepository.findByUser(user);
    }
    
    public List<Recipe> getFavourites(User user) {
    	List<FavouriteRecipe> favouriteRecipes = favouriteRecipeRepository.findByFavouriteRecipeIdUser(user);
    	List<Recipe> recipes = new ArrayList<>();
    	
    	for (var item: favouriteRecipes) {
    		recipes.add(item.getFavouriteRecipeId().getRecipe());
    	}
    	
    	return recipes;
    }
    
    public FavouriteRecipe addFavourite(User user, Recipe recipe) {
    	FavouriteRecipeId favouriteRecipeId = new FavouriteRecipeId();
    	favouriteRecipeId.setRecipe(recipe);
    	favouriteRecipeId.setUser(user);
    	
    	return favouriteRecipeRepository.save(new FavouriteRecipe(favouriteRecipeId, LocalDateTime.now()));
    }
   
    public void deleteFavourite(User user, Recipe recipe) {
    	FavouriteRecipeId favouriteRecipeId = new FavouriteRecipeId();
    	favouriteRecipeId.setRecipe(recipe);
    	favouriteRecipeId.setUser(user);
    	
    	favouriteRecipeRepository.deleteById(favouriteRecipeId);
    }
}
