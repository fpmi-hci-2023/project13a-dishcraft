package com.dishcraft.controller;

import com.dishcraft.model.FavouriteRecipe;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.payload.request.FavouriteRequest;
import com.dishcraft.payload.request.RecipeRequest;
import com.dishcraft.services.RecipeService;
import com.dishcraft.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    public UserController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
		this.recipeService = recipeService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);

        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //TODO: TEST
    @DeleteMapping("/users/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteUser(Authentication authentication, @PathVariable Long id) {
    	userService.deleteUserById(id);
    }
    
    // TODO: TEST
    @GetMapping("/users/{id}/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipesByUser(@PathVariable Long id) {
    	User user = userService.getUser(id);
    	
    	return user != null ? new ResponseEntity<>(userService.getAllRecipesByUser(user), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // TODO: TEST
    @DeleteMapping("/users/{id}/recipes/{recipe_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deleteUserRecipe(Authentication authentication, @PathVariable("id") Long id, @PathVariable("recipe_id") Long recipeId) {
    	
    	User user = userService.getUserByEmail(authentication.getName());
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	
    	if (recipe.getUser() != user) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    	
    	recipeService.deleteRecipe(recipeId);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // TODO: TEST
    @GetMapping("/users/{id}/favourites")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Recipe>> getFavourites(Authentication authentication, @PathVariable("id") Long id) {    	
    	User user = userService.getUserByEmail(authentication.getName());
    	
    	if (user.getId() != id)
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	
    	return new ResponseEntity<>(userService.getFavourites(user), HttpStatus.OK);
    }
    
    @PostMapping("/users/{id}/favourites")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<FavouriteRecipe> addFavourite(
    		Authentication authentication, 
    		@PathVariable("id") Long id,
    		@Valid @ModelAttribute FavouriteRequest favouriteRequest
    		) {
    	User user = userService.getUserByEmail(authentication.getName());
    	
    	if (user.getId() != id)
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	
    	Recipe recipe = recipeService.getRecipe(favouriteRequest.recipeId());
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(userService.addFavourite(user, recipe), HttpStatus.OK);
    }
    
    @DeleteMapping("/users/{id}/favourites/{recipe_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deleteFavourite(
    		Authentication authentication, 
    		@PathVariable("id") Long id,
    		@PathVariable("recipe_id") Long recipeId
    		) {
    	User user = userService.getUserByEmail(authentication.getName());
    	
    	if (user.getId() != id)
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	userService.deleteFavourite(user, recipe);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}
