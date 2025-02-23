package com.solarl.education;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestBean {

    @Value("${super.duper.test.phrase}")
    private String testPhrase;

    public void run() {
        System.out.println("Bean has been created and running: " + testPhrase);
    }
}
