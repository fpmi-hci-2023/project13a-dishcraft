package com.dishcraft.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dishcraft.model.Product;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeProduct;
import com.dishcraft.repositories.ProductRepository;
import com.dishcraft.repositories.RecipeProductRepository;


@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final RecipeProductRepository recipeProductRepository;
	
	public ProductService(ProductRepository productRepository, RecipeProductRepository recipeProductRepository) {
		this.productRepository = productRepository;
		this.recipeProductRepository = recipeProductRepository;
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseGet(null);
	}
	
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> getAllProducts() {
		return (List<Product>)productRepository.findAll();
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getProductsByRecipe(Recipe recipe) {
		List<RecipeProduct> list =  recipeProductRepository.findByRecipeProductIdRecipe(recipe);
		List<Product> products = new ArrayList();
		
		for (var item: list) {
			products.add(item.getRecipeProductId().getProduct());
		}
		
		return products;
	}
}
