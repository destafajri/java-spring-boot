package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

    private GreetingService greetingService;

    @Autowired
    public Greeting(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    public String greetings(){
        return greetingService.sayHello();
    }
}
