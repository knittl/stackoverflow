package com.example.spring;

import com.example.annotations.*;

@MyAnnotation
public class WrongService {
    public WrongService() {
        System.out.println("Do not instantiate me!");
//        throw new RuntimeException("I'm in the wrong package, I must NOT be instantiated");
    }
}
