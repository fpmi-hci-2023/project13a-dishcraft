package com.dishcraft.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dishcraft.model.Recipe;
import com.dishcraft.model.Step;

public interface StepRepository extends CrudRepository<Step, Long> {

	List<Step> findByRecipe(Recipe recipe);
}
