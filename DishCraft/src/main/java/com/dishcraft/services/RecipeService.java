package com.dishcraft.services;

import com.dishcraft.model.Recipe;
import com.dishcraft.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipeList() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }
    
    public void deleteRecipe(Long id) {
    	recipeRepository.deleteById(id);
    }
}
