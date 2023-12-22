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
	private final ImageService imageService;
	
	public ProductService(ProductRepository productRepository, RecipeProductRepository recipeProductRepository, ImageService imageService) {
		this.productRepository = productRepository;
		this.recipeProductRepository = recipeProductRepository;
		this.imageService = imageService;
	}
	
	public Product getProductById(Long id) {
		var product = productRepository.findById(id).orElseGet(null);
		if (product != null) {
    		product.getImage().setData(imageService.downloadImage(product.getImage()));
		}
		return product;
	}
	
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> getAllProducts() {
		var products = (List<Product>)productRepository.findAll();
    	for (var product: products) {
    		product.getImage().setData(imageService.downloadImage(product.getImage()));
    	}
		return products;
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getProductsByRecipe(Recipe recipe) {
		List<RecipeProduct> list =  getRecipeProductsByRecipe(recipe);
		List<Product> products = new ArrayList<>();
		
		for (var item: list) {
			products.add(item.getRecipeProductId().getProduct());
		}
		
		for (var product: products) {
    		product.getImage().setData(imageService.downloadImage(product.getImage()));
    	}
		
		return products;
	}
	
	public List<RecipeProduct> getRecipeProductsByRecipe(Recipe recipe) {
		return recipeProductRepository.findByRecipeProductIdRecipe(recipe);
	}
}
