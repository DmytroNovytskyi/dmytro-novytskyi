package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Import(SecondConfig.class)
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.epam.spring.homework2.bfpp",
        "com.epam.spring.homework2.bpp"})
public class FirstConfig {

    @Bean
    public BeanA beanA() {
        return new BeanA();
    }

    @Bean(initMethod = "initCheck", destroyMethod = "destroyCheck")
    @DependsOn("beanD")
    public BeanB beanB(@Value("${bean-b.name}") String name, @Value("${bean-b.value}") int value) {
        return new BeanB(name, value);
    }

    @Bean(initMethod = "initCheck", destroyMethod = "destroyCheck")
    @DependsOn({"beanB", "beanD"})
    public BeanC beanC(@Value("${bean-c.name}") String name, @Value("${bean-c.value}") int value) {
        return new BeanC(name, value);
    }

    @Bean(initMethod = "initCheck", destroyMethod = "destroyCheck")
    public BeanD beanD(@Value("${bean-d.name}") String name, @Value("${bean-d.value}") int value) {
        return new BeanD(name, value);
    }

    @Bean
    public BeanE beanE() {
        return new BeanE();
    }

    @Bean
    @Lazy
    public BeanF beanF() {
        return new BeanF();
    }

}
