package com.e_garden.api.Exception;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ExceptionController {
    @GetMapping("/")
    public String HelloWork() {
        return "API is Working";
    }
}
