package com.dishcraft.controller;

import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.services.RecipeService;
import com.dishcraft.services.UserService;

import javax.sound.midi.VoiceStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void deleteUser(@PathVariable Long id) {
    	userService.deleteUserById(id);
    }
    
    // TODO: TEST
    @GetMapping("/users/{id}/recipes")
    public ResponseEntity<?> getAllRecipesByUser(@PathVariable Long id) {
    	User user = userService.getUser(id);
    	
    	return user != null ? new ResponseEntity<>(userService.getAllRecipesByUser(user), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // TODO: TEST
    @DeleteMapping("/users/{id}/recipes/{recipe_id}")
    public ResponseEntity<?> deleteUserRecipe(Authentication authentication, @PathVariable("id") Long id, @PathVariable("recipe_id") Long recipeId) {
    	
    	User user = userService.getUserByEmail(authentication.getName());
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	
    	if (recipe.getUser() != user) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    	
    	recipeService.deleteRecipe(recipeId);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
//    
//    @GetMapping("/users/{id}/favourites")
//    public ResponseEntity<?> getFavourites(Authentication authentication, @PathVariable("id") Long id) {    	
//    	User user = userService.getUserByEmail(authentication.getName());
//    	
//    	if (user.getId() != id)
//    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    	
//    	
//    }
}
