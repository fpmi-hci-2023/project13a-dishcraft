package com.dishcraft.controller;

import com.dishcraft.model.Comment;
import com.dishcraft.model.Image;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.model.Rating;
import com.dishcraft.payload.request.CommentRequest;
import com.dishcraft.payload.request.RatingRequest;
import com.dishcraft.payload.request.RecipeRequest;
import com.dishcraft.repositories.UserRepository;
import com.dishcraft.services.CommentService;
import com.dishcraft.services.ImageService;
import com.dishcraft.services.ProductService;
import com.dishcraft.services.RatingService;
import com.dishcraft.services.RecipeComplexityService;
import com.dishcraft.services.RecipeService;

import jakarta.validation.Valid;

import org.aspectj.weaver.patterns.VoidArrayFinder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final ProductService productService;
    private final RatingService ratingService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final RecipeComplexityService recipeComplexityService;
    private final CommentService commentService;

    public RecipeController(RecipeService recipeService, ImageService imageService, 
    		UserRepository userRepository, RecipeComplexityService recipeComplexityService, 
    		CommentService commentService, ProductService productService, RatingService ratingService) {
        this.recipeService = recipeService;
		this.ratingService = ratingService;
		this.imageService = imageService;
		this.userRepository = userRepository;
		this.recipeComplexityService = recipeComplexityService;
		this.commentService = commentService;
		this.productService = productService;
    }

    // протестировать
    @PostMapping("/recipes")
    public ResponseEntity<?> postRecipes(Authentication authentication, @Valid @ModelAttribute RecipeRequest recipe) {
    	Image imageData = null;
    	
    	try {
			imageData = imageService.uploadImage(recipe.getImageFile());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Recipe newRecipe = recipeService.saveRecipe(new Recipe(
    										recipe.getRecipeName(),
    										recipe.getDescription(),
    										imageData,
    										recipe.getCookingTimeMinutes(),
    										recipe.getReadyTimeMinutes(),
    										recipeComplexityService.getComplexityById(recipe.getComplexityId()),
    										recipe.getDefaultPortionsNumber(),
    										user));
    	
    	return newRecipe != null ? new ResponseEntity<>(newRecipe, HttpStatus.CREATED)
                				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/recipes")
    public List<Recipe> getRecipeList() {
    	
        return recipeService.getRecipeList();
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);

        return recipe != null ? new ResponseEntity<>(recipe, HttpStatus.OK)
                              : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/recipes/{id}")
    public void deleteRecipe(@PathVariable Long id) {
    	recipeService.deleteRecipe(id);
    }
    
    @GetMapping("/recipes/{id}/image")
//    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.IMAGE_JPEG);
    	return new ResponseEntity<>(imageService.downloadImage(recipe.getImage()), headers, HttpStatus.OK);
//        return imageService.downloadImage(recipe.getImage());
    }
    
    
    @PostMapping("/recipes/{id}/comments")
    public ResponseEntity<?> createComment(Authentication authentication, 
    		@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) {
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Comment comment = commentService.saveComment(
    			new Comment(commentRequest.commentText()), user, recipeService.getRecipe(id));
    	
    	return comment != null ? new ResponseEntity<>(comment, HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/recipes/{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long id) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	return recipe != null ? new ResponseEntity<>(
    			commentService.getCommentsByRecipe(recipe), HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/recipes/{id}/comments/{comment_id}")
    public ResponseEntity<?> getCommentByRecipe(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "comment_id") Long commentId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	Comment comment = commentService.getCommentByRecipe(commentId, recipe);
    	
    	return comment != null ? new ResponseEntity<>(comment, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PatchMapping("/recipes/{id}/comments/{comment_id}")
    public ResponseEntity<?> changeComment(Authentication authentication, @PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "comment_id") Long commentId, @Valid @RequestBody CommentRequest commentRequest) {
    	
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Comment comment = commentService.getCommentByRecipe(commentId, recipe);
    	
    	if (comment == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	if (!comment.getUser().equals(user))
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	
    	comment.setCommentText(commentRequest.commentText());
    	return new ResponseEntity <>(commentService.saveComment(comment), HttpStatus.OK);
    }
    
    // TODO: TEST
    @DeleteMapping("/recipes/{id}/comments/{comment_id}") 
    public void deleteComment(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "comment_id") Long commentId){
  
    	commentService.deleteComment(commentId);
    }
    
    @PostMapping("/recipes/{id}/ratings")
    public ResponseEntity<?> addRating(Authentication authentication, @PathVariable(name = "id") Long recipeId
    		, @Valid @RequestBody RatingRequest ratingRequest) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Rating rating = ratingService.addRating(user, recipe, ratingRequest.rating());
    	
    	return rating != null ? new ResponseEntity<>(rating, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    // TODO: TEST
    @GetMapping("/recipes/{id}/ratings")
    public ResponseEntity<?> getRatings(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(ratingService.getRatings(recipe), HttpStatus.OK);
    }
    
    // TODO: TEST
    @GetMapping("/recipes/{id}/ratings/total")
    public ResponseEntity<?> getTotalRating(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(ratingService.getTotalRating(recipeId), HttpStatus.OK);
    }
    
    @GetMapping("/recipes/{id}/ratings/{rating_id}")
    public ResponseEntity<?> getTotalRating(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "rating_id") Long ratingId) {
    	
    	Rating rating = ratingService.getRating(ratingId);
    	
    	return rating != null ? new ResponseEntity<>(rating, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // TODO: TEST
    @PatchMapping("/recipes/{id}/ratings/{rating_id}")
    public ResponseEntity<?> changeRating(Authentication authentication, @PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "rating_id") Long ratingId, @Valid @RequestBody RatingRequest ratingRequest) {
    	
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Rating rating = ratingService.getRating(ratingId);
    	
    	if (rating == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	if (!rating.getUser().equals(user))
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	
    	rating.setRatingValue(ratingRequest.rating());
    	return new ResponseEntity <>(ratingService.saveRating(rating), HttpStatus.OK);
    }
    
    //TODO: TEST
    @DeleteMapping("/recipes/{id}/ratings/{rating_id}")
    public void deleteRating(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "rating_id") Long ratingId) {
    	ratingService.deleteRating(ratingId);
    }
    
    
    
    @GetMapping("/recipes/{id}/products")
    public ResponseEntity<?> getRecipeProducts(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(productService.getProductsByRecipe(recipe), HttpStatus.OK);
    }
}
    
