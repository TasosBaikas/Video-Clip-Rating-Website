package com.example.videocliprating.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Refers to src/main/resources/templates/index.html
    }

    @GetMapping("/home")
    public String home() {
        return "index"; // Refers to src/main/resources/templates/index.html
    }
}
