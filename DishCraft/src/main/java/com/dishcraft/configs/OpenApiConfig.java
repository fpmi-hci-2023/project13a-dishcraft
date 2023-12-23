package com.dishcraft.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//@OpenAPIDefinition(
//        info = @Info(
//                title = "DishCraft API",
//                description = "Dish Craft", version = "1.0.0",
//                contact = @Contact(
//                        name = "Veronika Bystrova"
//                )
//        )
//)
//public class OpenApiConfig {
//}
@Configuration
@SecurityScheme(
		  name = "Bearer Authentication",
		  type = SecuritySchemeType.HTTP,
		  bearerFormat = "JWT",
		  scheme = "bearer"
		)
public class OpenApiConfig {

	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("DishCraft API")
						.description("Dish Craft")
						.version("v1.0.0"))
						;
	}
}

