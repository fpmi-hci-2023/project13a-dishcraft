package com.dishcraft.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dishcraft.model.Rating;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repositories.RatingRepository;

@Service
public class RatingService {

	private final RatingRepository ratingRepository;
	
	public RatingService(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
		
	}
	
	public Rating getRating(Long id) {
		return ratingRepository.findById(id).orElseGet(null);
	}
	
	public Rating addRating(User user, Recipe recipe, Integer rating) {
		return ratingRepository.save(new Rating(
					rating,
					user,
					recipe
				));
	}
	
	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}
	
	public List<Rating> getRatings (Recipe recipe) {
		return ratingRepository.findByRecipe(recipe);
	}
	
	public Double getTotalRating(Long recipeId) {
		return ratingRepository.avgByRecipe(recipeId);
	}
	
	public void deleteRating(Long ratingId) {
		ratingRepository.deleteById(ratingId);
	}
}
