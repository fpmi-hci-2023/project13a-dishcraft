package com.dishcraft.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dishcraft.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
	Optional<Image> findByFileName(String filename);
	
}
