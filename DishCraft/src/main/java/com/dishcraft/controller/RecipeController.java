package com.dishcraft.controller;

import com.dishcraft.model.Comment;
import com.dishcraft.model.Image;
import com.dishcraft.model.Product;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.SortRecipesEnum;
import com.dishcraft.model.Step;
import com.dishcraft.model.User;
import com.dishcraft.model.Rating;
import com.dishcraft.payload.request.CommentRequest;
import com.dishcraft.payload.request.RatingRequest;
import com.dishcraft.payload.request.RecipeRequest;
import com.dishcraft.payload.request.StepRequest;
import com.dishcraft.payload.response.NutritionalResponse;
import com.dishcraft.repositories.UserRepository;
import com.dishcraft.services.CommentService;
import com.dishcraft.services.ImageService;
import com.dishcraft.services.ProductService;
import com.dishcraft.services.RatingService;
import com.dishcraft.services.RecipeComplexityService;
import com.dishcraft.services.RecipeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.aspectj.weaver.patterns.VoidArrayFinder;
import org.springframework.data.domain.Page;
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

    @PostMapping("/recipes")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Recipe> postRecipes(Authentication authentication, 
    		@Valid @ModelAttribute RecipeRequest recipe) {
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
    	
    	for (var item: recipe.getProducts()) {
    		recipeService.addProduct(newRecipe, item);    	}
    	
    	for (var item: recipe.getSteps()) {
    		recipeService.addStep(newRecipe, item);
    	}
    	
    	return newRecipe != null ? new ResponseEntity<>(newRecipe, HttpStatus.CREATED)
                				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/recipes")
    public Page<Recipe> getRecipeList(
    		@RequestParam(required = false) String cookingTime,
    		@RequestParam(required = false) String calories,
    		@RequestParam(required = false) List<Long> productIds,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(required = false, defaultValue = "new") String sortBy) {
    	
        return recipeService.getRecipeList(cookingTime, calories, productIds, page, size, sortBy);
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
    
    @PostMapping("/recipes/{id}/steps")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Step> addStep(Authentication authentication, 
    		@PathVariable Long id, 
    		@Valid @ModelAttribute StepRequest stepRequest) {
    	
    	Recipe recipe = recipeService.getRecipe(id);
    
    	if (recipe == null) 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(recipeService.addStep(recipe, stepRequest), HttpStatus.OK);
    }
    
    @GetMapping("/recipes/{id}/steps")
    public ResponseEntity<List<Step>> getSteps(@PathVariable Long id) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	if (recipe == null) 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(recipeService.getSteps(recipe), HttpStatus.OK);
    }
    
    @GetMapping("/recipes/{id}/steps/{step_id}")
    public ResponseEntity<Step> getStepById(@PathVariable("id") Long id, @PathVariable("step_id") Long stepId) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	if (recipe == null) 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   
    	Step step = recipeService.getStep(stepId);
    	
    	return (step != null && step.getRecipe().getRecipeId() == id) 
    			? new ResponseEntity<>(step, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/recipes/{id}/steps/{step_id}/image")
    public ResponseEntity<byte[]> getStepImageById(@PathVariable("id") Long id, @PathVariable("step_id") Long stepId) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	if (recipe == null) 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   
    	Step step = recipeService.getStep(stepId);
    	
    	if (step == null || step.getRecipe().getRecipeId() != id) 
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.IMAGE_JPEG);
    	return new ResponseEntity<>(imageService.downloadImage(step.getImage()), headers, HttpStatus.OK);
//        return imageService.downloadImage(recipe.getImage());
    }
    
    // TODO: TEST
    @GetMapping("/recipes/{id}/nutritional_value")
    public ResponseEntity<NutritionalResponse> getNutritionalValue(@PathVariable Long id) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	return recipe != null ? new ResponseEntity<>(
    			recipeService.calculateNutritional(recipe), HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/recipes/{id}/comments")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Comment> createComment(Authentication authentication, 
    		@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) {
    	
    	User user = userRepository.findByEmail(authentication.getName());
    	
    	Comment comment = commentService.saveComment(
    			new Comment(commentRequest.commentText()), user, recipeService.getRecipe(id));
    	
    	return comment != null ? new ResponseEntity<>(comment, HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/recipes/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
    	Recipe recipe = recipeService.getRecipe(id);
    	
    	return recipe != null ? new ResponseEntity<>(
    			commentService.getCommentsByRecipe(recipe), HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/recipes/{id}/comments/{comment_id}")
    public ResponseEntity<Comment> getCommentByRecipe(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "comment_id") Long commentId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	Comment comment = commentService.getCommentByRecipe(commentId, recipe);
    	
    	return comment != null ? new ResponseEntity<>(comment, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PatchMapping("/recipes/{id}/comments/{comment_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Comment> changeComment(Authentication authentication, @PathVariable(name = "id") Long recipeId, 
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Rating> addRating(Authentication authentication, @PathVariable(name = "id") Long recipeId
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
    public ResponseEntity<List<Rating>> getRatings(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(ratingService.getRatings(recipe), HttpStatus.OK);
    }
    
    // TODO: TEST
    @GetMapping("/recipes/{id}/ratings/total")
    public ResponseEntity<Double> getTotalRating(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(ratingService.getTotalRating(recipeId), HttpStatus.OK);
    }
    
    @GetMapping("/recipes/{id}/ratings/{rating_id}")
    public ResponseEntity<Rating> getTotalRating(@PathVariable(name = "id") Long recipeId, 
    		@PathVariable(name = "rating_id") Long ratingId) {
    	
    	Rating rating = ratingService.getRating(ratingId);
    	
    	return rating != null ? new ResponseEntity<>(rating, HttpStatus.OK)
    			: new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // TODO: TEST
    @PatchMapping("/recipes/{id}/ratings/{rating_id}")
    public ResponseEntity<Rating> changeRating(Authentication authentication, @PathVariable(name = "id") Long recipeId, 
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
    public ResponseEntity<List<Product>> getRecipeProducts(@PathVariable(name = "id") Long recipeId) {
    	Recipe recipe = recipeService.getRecipe(recipeId);
    	if (recipe == null)
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(productService.getProductsByRecipe(recipe), HttpStatus.OK);
    }
}
    
