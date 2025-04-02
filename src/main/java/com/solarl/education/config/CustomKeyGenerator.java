package com.solarl.education.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return "MY_KEY_" + Arrays.stream(params)
                .map(Object::toString)
                .collect(Collectors.joining("_"));
    }
}
