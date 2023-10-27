package com.dishcraft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dishcraft.model.RecipeComplexity;

public interface RecipeComplexityRepository extends JpaRepository<RecipeComplexity, Integer> {

}
