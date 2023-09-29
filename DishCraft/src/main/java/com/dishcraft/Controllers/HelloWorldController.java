package com.dishcraft.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Hello World контроллер", description="Тестовый контроллер")
@RestController
public class HelloWorldController {

    @GetMapping("/sayhello")
    public String sayHelloWorld(){
        return "Hello World!";
    }
    
}
