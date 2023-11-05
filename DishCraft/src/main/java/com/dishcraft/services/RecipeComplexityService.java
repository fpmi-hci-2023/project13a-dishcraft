package com.dishcraft.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dishcraft.model.RecipeComplexity;
import com.dishcraft.repositories.RecipeComplexityRepository;

@Service
public class RecipeComplexityService {

	private final RecipeComplexityRepository recipeComplexityRepository;
	
	public RecipeComplexityService(RecipeComplexityRepository recipeComplexityRepository) {
		this.recipeComplexityRepository = recipeComplexityRepository;
	}
	
	public List<RecipeComplexity> getRecipeComplexities() {
		return recipeComplexityRepository.findAll();
	}
	
	public RecipeComplexity getComplexityById(Integer id) {
		return recipeComplexityRepository.findById(id).get();
	}
}
