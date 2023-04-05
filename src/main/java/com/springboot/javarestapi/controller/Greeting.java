package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.services.GreetingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class Greeting {
    private final GreetingService greetingService;

    @GetMapping("/")
    public String greetings() {
        log.trace("greeting tracing");
        log.debug("greeting debugging");
        log.info("greeting request is started running");
        return greetingService.sayHello();
    }
}
