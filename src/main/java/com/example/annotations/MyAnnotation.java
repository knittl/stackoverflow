package com.example.annotations;

import java.lang.annotation.*;

// not annotated with @Component, because this package should be fully independent of Spring
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
