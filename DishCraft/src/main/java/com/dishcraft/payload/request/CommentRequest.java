package com.dishcraft.payload.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
		@NotBlank
		String commentText
) {}
