package com.example.demo.custom.services;

import com.example.annotations.*;

@MyAnnotation
public class MyService {
    public MyService() {
        System.out.println("Look, I'm a bean now!");
    }
}
