package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Value("${welcome.text}")
    private String welcomeText;
    @Override
    public String sayHello() {
        return welcomeText;
    }
}
