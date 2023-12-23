package com.dishcraft.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dishcraft.model.Comment;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repositories.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
		
	}
	
	public Comment saveComment(Comment comment, User user, Recipe recipe) {
		// TODO: разобраться с датой
		// comment.setCommentData(null);
		comment.setCommentDate(LocalDateTime.now());
		comment.setUser(user);
		comment.setRecipe(recipe);
		return commentRepository.save(comment);
	}
	
	public Comment saveComment(Comment comment) {
		// TODO: разобраться с датой
		// comment.setCommentData(null);
//		comment.setCommentDate(LocalDateTime.now());
//		comment.setUser(user);
//		comment.setRecipe(recipe);
		return commentRepository.save(comment);
	}
	
	public List<Comment> getCommentsByRecipe(Recipe recipe) {
		return commentRepository.findByRecipe(recipe);
	}
	
	public Comment getCommentByRecipe(Long id, Recipe recipe) {
		return commentRepository.findByCommentIdAndRecipe(id, recipe).orElseGet(null);
	}
	
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}
}
