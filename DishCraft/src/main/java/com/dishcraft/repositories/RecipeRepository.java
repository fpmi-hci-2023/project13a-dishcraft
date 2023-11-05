package com.dishcraft.repositories;

import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	List<Recipe> findByUser(User user);
	
	void deleteByUser(User user);
	
}
