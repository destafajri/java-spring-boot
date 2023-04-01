package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.services.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Greeting {

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/")
    public String greetings() {
        log.trace("greeting tracing");
        log.debug("greeting debugging");
        log.info("greeting info running");
        return greetingService.sayHello();
    }
}
