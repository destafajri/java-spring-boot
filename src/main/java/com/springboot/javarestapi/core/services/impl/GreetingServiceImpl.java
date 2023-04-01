package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.config.ApplicationProperties;
import com.springboot.javarestapi.config.CredentialProperties;
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
    private CredentialProperties jwtkey;

    @Autowired
    public GreetingServiceImpl(ApplicationProperties appProp, CredentialProperties jwtkey) {
        this.appProp = appProp;
        this.jwtkey = jwtkey;
    }

    @Override
    public String sayHello() {
        TimeZone timezone = TimeZone.getTimeZone(appProp.getTimezone());
        return welcomeText + " our timezone is " + timezone.getDisplayName();
    }
}
