package com.dishcraft.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "DishCraft API",
                description = "Dish Craft", version = "1.0.0",
                contact = @Contact(
                        name = "Veronika Bystrova"
                )
        )
)
public class OpenApiConfig {
}
