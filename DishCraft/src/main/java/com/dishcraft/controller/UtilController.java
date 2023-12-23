package com.dishcraft.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dishcraft.model.FilterCaloriesEnum;
import com.dishcraft.model.FilterTimeEnum;
import com.dishcraft.model.MeasureUnit;
import com.dishcraft.model.RecipeComplexity;
import com.dishcraft.services.RecipeComplexityService;
import com.dishcraft.services.UnitsServer;

@CrossOrigin
@RestController
public class UtilController {
	
	private final UnitsServer unitsServer;
	private final RecipeComplexityService recipeComplexityService;
	
	public UtilController(UnitsServer unitsServer, RecipeComplexityService recipeComplexityService) {
		this.unitsServer = unitsServer;
		this.recipeComplexityService = recipeComplexityService;
	}
	
	@GetMapping("/utils/filter-calories")
	public FilterCaloriesEnum[] getFilterCalories() {
		return FilterCaloriesEnum.values();
	}
	
	@GetMapping("/utils/filter-time")
	public FilterTimeEnum[] getFilterTime() {
		return FilterTimeEnum.values();
	}
	
	@GetMapping("/utils/measure-unit")
	public List<MeasureUnit> getMeasureUnits() {
		return unitsServer.getMeasureUnits();
	}
	
	@GetMapping("/utils/complexity")
	public List<RecipeComplexity> getRecipeComplexities() {
		return recipeComplexityService.getRecipeComplexities();
	}
}
