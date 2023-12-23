package com.dishcraft.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dishcraft.model.Comment;
import com.dishcraft.model.Recipe;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByRecipe(Recipe recipe);
	
	Optional<Comment> findByCommentIdAndRecipe(Long commentId, Recipe recipe);
}
