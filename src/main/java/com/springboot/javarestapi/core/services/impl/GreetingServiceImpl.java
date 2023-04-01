package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Value("${welcome.text}")
    private String welcomeText;

    @Value("${timezone}")
    private String timezoneLoc;

    @Override
    public String sayHello() {
        TimeZone timezone = TimeZone.getTimeZone(timezoneLoc);
        return welcomeText + " our timezone is "+ timezone.getDisplayName();
    }
}
