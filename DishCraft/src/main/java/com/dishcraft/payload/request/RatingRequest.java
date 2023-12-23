package com.dishcraft.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RatingRequest(
		@NotBlank
		@Min(value = 1)
		@Max(value = 5)
		Integer rating
) {}
