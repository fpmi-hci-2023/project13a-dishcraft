package com.dishcraft.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	
	private String commentText;
	
	// TODO: посмотреть как делается Data
	@Column(name = "comment_date", columnDefinition = "timestamp")
	private LocalDateTime commentDate;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	private Comment() {}
	
	public Comment(String commentText) {
		super();
		this.commentText = commentText;
	}
	
	public Comment(String commentText, LocalDateTime commentDate, User user, Recipe recipe) {
		super();
		this.commentText = commentText;
		this.commentDate = commentDate;
		this.user = user;
		this.recipe = recipe;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public LocalDateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDateTime commentDate) {
		this.commentDate = commentDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
}
