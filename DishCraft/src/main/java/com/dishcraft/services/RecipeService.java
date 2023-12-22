package com.dishcraft.services;

import com.dishcraft.model.FilterCaloriesEnum;
import com.dishcraft.model.FilterTimeEnum;
import com.dishcraft.model.Image;
import com.dishcraft.model.MeasureUnit;
import com.dishcraft.model.Product;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeProduct;
import com.dishcraft.model.RecipeProductId;
import com.dishcraft.model.SortRecipesEnum;
import com.dishcraft.model.Step;
import com.dishcraft.payload.request.RecipeProductRequest;
import com.dishcraft.payload.request.StepRequest;
import com.dishcraft.payload.response.NutritionalResponse;
import com.dishcraft.repositories.RecipeProductRepository;
import com.dishcraft.repositories.RecipeRepository;
import com.dishcraft.repositories.StepRepository;

import jakarta.persistence.criteria.Predicate;

import org.hibernate.service.spi.ServiceBinding.ServiceLifecycleOwner;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.Data;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RatingService ratingService;
    private final ProductService productService;
    private final StepRepository stepRepository;
    private final ImageService imageService;
    private final UnitsServer unitsServer;
    private final RecipeProductRepository recipeProductRepository;

    public RecipeService(RecipeRepository recipeRepository, ProductService productService, StepRepository stepRepository, ImageService imageService, UnitsServer unitsServer, RecipeProductRepository recipeProductRepository, RatingService ratingService) {
        this.recipeRepository = recipeRepository;
		this.ratingService = ratingService;
		this.productService = productService;
		this.stepRepository = stepRepository;
		this.imageService = imageService;
		this.unitsServer = unitsServer;
		this.recipeProductRepository = recipeProductRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Page<Recipe> getRecipeList(
    		String cookingTime, String calories,List<Long> productIds, 
    		int page, int size, String sortBy, String search
    		) {
    	
    	List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
    	
    	if (productIds != null) {
    		Iterator<Recipe> recipeIterator = recipes.iterator();
        	while (recipeIterator.hasNext()) {
        		var recipe = recipeIterator.next();
        		
        		var products = productService
        				.getProductsByRecipe(recipe)
        				.stream().map(Product::getProductId).toList();
        		
        		for (var id: productIds) {
        			if (!products.contains(id)) {
        				recipeIterator.remove();
        			}
        		}
        	}
    	}
    	
    	if (cookingTime != null) {
    		try {
    			recipes = recipes.stream()
	    				.filter(FilterTimeEnum
	    						.valueOf(cookingTime.toUpperCase())::filter)
	    				.toList();
    		} catch(IllegalArgumentException ex) {
    			return null;
    		}
    	}
    	
    	if (calories != null) {
    		try {
    			recipes = recipes.stream()
	    				.filter(x -> FilterCaloriesEnum
	    						.valueOf(calories.toUpperCase())
	    						.filter(this.calculateNutritional(x).getCalories())
	    						)
	    				.toList();
    		} catch(IllegalArgumentException ex) {
    			return null;
    		}
    	}
    	
//    	System.out.println("Before new");
    	if (sortBy.equals("new") && recipes != null && recipes.size() > 0) {
    		System.out.println("In new");
    		recipes.sort(Comparator.comparing(Recipe::getRecipeId).reversed());
    	} else if (sortBy.equals("popular")) {
    		recipes.sort(
    			Comparator.comparing(Recipe::getRecipeId, 
    				(r1, r2) -> {
    					Double t1 = ratingService.getTotalRating(r1);
    					Double t2 = ratingService.getTotalRating(r2);
		    			return Double.compare(t1 == null ? 0 : t1,
		    					t2 == null ? 0 : t2);
    				})
    			.reversed());
    	}
    	
    	if (search != null) {
    		recipes = recipes.stream()
    				.filter(x -> x.getRecipeName().toLowerCase()
    						.contains(search.toLowerCase())).toList();
    	}
    	
    	for (var recipe: recipes) {
//    		image_data = recipe.getImage().getData();
    		System.out.println(imageService.downloadImage(recipe.getImage()));
    		recipe.getImage().setData(imageService.downloadImage(recipe.getImage()));
    		System.out.println(recipe.getImage().getData());
    	}
    	
    	PageRequest pageRequest = PageRequest.of(page, size);
    	int start = (int) pageRequest.getOffset();
    	int end = Math.min((start + pageRequest.getPageSize()), recipes.size());
    	
        return new PageImpl<>(recipes.subList(start, end), pageRequest, recipes.size());
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }
    
    public void deleteRecipe(Long id) {
    	recipeRepository.deleteById(id);
    }
    
    public NutritionalResponse calculateNutritional(Recipe recipe) {
    	List<RecipeProduct> recipeProducts = productService.getRecipeProductsByRecipe(recipe);

    	int value1 = 0;
    	float value2 = 0f, value3 = 0f, value4 = 0f;
    	for (var item: recipeProducts) {
    		Product product = item.getRecipeProductId().getProduct();
    		
			value1 += item.getGrams() * product.getCalories() / 100;
			value2 += item.getGrams() * product.getProteins() / 100;
			value3 += item.getGrams() * product.getFats() / 100;
			value4 += item.getGrams() * product.getCarbohydrates() / 100;
		}
    	
    	return new NutritionalResponse(value1, value2, value3, value4);
    }
    
    public Step addStep(Recipe recipe, StepRequest stepRequest) {
    	Image imageData = null;
    	
    	try {
			imageData = imageService.uploadImage(stepRequest.getImageFile());
		} catch (IOException e) {
			return null;
		}
    	
    	Step step = new Step(stepRequest.getStepDescription(),
    						imageData,
    						stepRequest.getNumberInRecipe(),
    						recipe);
    	return stepRepository.save(step);
    }
    
    public RecipeProduct addProduct(Recipe recipe, RecipeProductRequest recipeProductRequest) {
    	Product product = productService.getProductById(recipeProductRequest.getProductId());
    	MeasureUnit measureUnit = unitsServer.getMeasureUnitById(recipeProductRequest.getMeasureUnitId());
    	
    	if (product == null || measureUnit == null)
    		return null;
    	
    	RecipeProductId recipeProductId = new RecipeProductId();
    	recipeProductId.setProduct(product);
    	recipeProductId.setRecipe(recipe);
    	
    	RecipeProduct recipeProduct = new RecipeProduct(
    				recipeProductId,
    				recipeProductRequest.getUnitsAmount(),
    				measureUnit,
    				recipeProductRequest.getGrams()
    			);
    	return recipeProductRepository.save(recipeProduct);
    }
    
    public List<Step> getSteps(Recipe recipe) {
    	var steps = (List<Step>)stepRepository.findByRecipe(recipe);
    	for (var step: steps) {
//    		System.out.println(imageService.downloadImage(recipe.getImage()));
    		step.getImage().setData(imageService.downloadImage(step.getImage()));
//    		System.out.println(product.getImage().getData());
    	}
		return steps;
    }
    
    public Step getStep (Long id) {
    	var step = stepRepository.findById(id).orElse(null);
    	step.getImage().setData(imageService.downloadImage(step.getImage()));
    	return step;
    }
}
