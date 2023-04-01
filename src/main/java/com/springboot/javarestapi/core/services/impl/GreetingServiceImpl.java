package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.config.ApplicationProperties;
import com.springboot.javarestapi.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Value("${welcome.text}")
    private String welcomeText;
    private ApplicationProperties appProp;

    @Autowired
    public GreetingServiceImpl(ApplicationProperties appProp) {
        this.appProp = appProp;
    }

    @Override
    public String sayHello() {
        TimeZone timezone = TimeZone.getTimeZone(appProp.getTimezone());
        return welcomeText + " our timezone is "+ timezone.getDisplayName();
    }
}
