package com.dishcraft.services;

import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repositories.RecipeRepository;
import com.dishcraft.repositories.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public UserService(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
		this.recipeRepository = recipeRepository;
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
   
}
