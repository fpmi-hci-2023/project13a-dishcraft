package com.dishcraft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dishcraft.model.Rating;
import com.dishcraft.model.Recipe;

public interface RatingRepository extends CrudRepository<Rating, Long> {

	List<Rating> findByRecipe(Recipe recipe);
	
	@Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.recipe.recipeId =: id")
	Double avgByRecipe(@Param("id") Long id);
}
