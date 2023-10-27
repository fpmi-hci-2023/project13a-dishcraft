package com.dishcraft.repositories;

import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	List<Recipe> findByUser(User user);
	
	void deleteByUser(User user);
	
}
