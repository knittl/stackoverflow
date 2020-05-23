package com.example.spring;

import com.example.annotations.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.*;

import java.lang.annotation.*;

@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableCustom.EnableCustomConfiguration.class)
@ComponentScan(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = MyAnnotation.class))
//@Import(EntityScanPackages.Registrar.class)
public @interface EnableCustom {
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] basePackages() default {};

    //    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ANNOTATION,
                    value = MyAnnotation.class))
    class EnableCustomConfiguration {
    }
}
