package com.example.spring;

import com.example.annotations.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.core.env.*;
import org.springframework.core.type.*;
import org.springframework.core.type.filter.*;

import java.lang.annotation.*;
import java.util.*;

@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableCustom.EnableCustomConfiguration.class)
public @interface EnableCustom {
    @AliasFor(attribute = "basePackages")
    String[] value() default {};

    @AliasFor(attribute = "value")
    String[] basePackages() default {};

    class EnableCustomConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {
        private static final BeanNameGenerator BEAN_NAME_GENERATOR = AnnotationBeanNameGenerator.INSTANCE;
        private Environment environment;

        @Override
        public void setEnvironment(final Environment environment) {
            this.environment = environment;
        }

        @Override
        public void registerBeanDefinitions(
                final AnnotationMetadata metadata,
                final BeanDefinitionRegistry registry) {
            final AnnotationAttributes annotationAttributes = new AnnotationAttributes(
                    metadata.getAnnotationAttributes(EnableCustom.class.getCanonicalName()));

            final ClassPathScanningCandidateComponentProvider provider
                    = new ClassPathScanningCandidateComponentProvider(false, environment);
            provider.addIncludeFilter(new AnnotationTypeFilter(MyAnnotation.class, true));

            final Set<String> basePackages
                    = getBasePackages((StandardAnnotationMetadata) metadata, annotationAttributes);

            for (final String basePackage : basePackages) {
                for (final BeanDefinition beanDefinition : provider.findCandidateComponents(basePackage)) {
                    final String beanClassName = BEAN_NAME_GENERATOR.generateBeanName(beanDefinition, registry);
                    if (!registry.containsBeanDefinition(beanClassName)) {
                        registry.registerBeanDefinition(beanClassName, beanDefinition);
                    }
                }
            }
        }

        private static Set<String> getBasePackages(
                final StandardAnnotationMetadata metadata,
                final AnnotationAttributes attributes) {
            final String[] basePackages = attributes.getStringArray("basePackages");
            final Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(basePackages));

            if (packagesToScan.isEmpty()) {
                // If value attribute is not set, fallback to the package of the annotated class
                return Collections.singleton(metadata.getIntrospectedClass().getPackage().getName());
            }

            return packagesToScan;
        }
    }
}
