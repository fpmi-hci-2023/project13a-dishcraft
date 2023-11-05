package com.dishcraft.payload.request;

import jakarta.validation.constraints.NotBlank;

public record FavouriteRequest(
		@NotBlank
		Long recipeId
) {}
