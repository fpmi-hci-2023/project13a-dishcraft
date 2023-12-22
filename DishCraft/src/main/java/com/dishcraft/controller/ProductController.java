package com.dishcraft.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dishcraft.services.ImageService;
import com.dishcraft.services.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import com.dishcraft.model.Image;
import com.dishcraft.model.Product;
import com.dishcraft.payload.request.ProductRequest;

@CrossOrigin
@RestController
public class ProductController {
	
	private final ProductService productService;
	private final ImageService imageService;
	
	public ProductController(ProductService productService, ImageService imageService) {
		this.productService = productService;
		this.imageService = imageService;
		
	}
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@PostMapping("/products")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<Product> createProduct(Authentication auth, @Valid @ModelAttribute ProductRequest productRequest) {
		Image imageData = null;
    	
    	try {
			imageData = imageService.uploadImage(productRequest.getImageFile());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    	
    	Product product = productService.saveProduct(new Product(
    													productRequest.getProductName(),
    													imageData,
    													productRequest.getCalories(),
    													productRequest.getProteins(),
    													productRequest.getFats(),
    													productRequest.getCarbohydrates()
    												));
    	
    	return product != null ? new ResponseEntity<>(product, HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.getProductWithImage(id);
		
		return product != null ? new ResponseEntity<>(product, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/products/{id}")
	@SecurityRequirement(name = "Bearer Authentication")
	public void deleteProduct(Authentication authentication, @PathVariable Long id) {
		productService.deleteProductById(id);
	}
	
	@GetMapping("/products/{id}/image")
	public ResponseEntity<byte[]> getProductImageById(@PathVariable Long id) {
		Product product = productService.getProductWithImage(id);
		
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.IMAGE_JPEG); // Установка типа содержимого для изображения
    	return new ResponseEntity<>(product.getImage().getData(), headers, HttpStatus.OK);
	}
}
