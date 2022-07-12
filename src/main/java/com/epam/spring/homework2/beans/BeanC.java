package com.epam.spring.homework2.beans;

public class BeanC extends BeanMaster {

    public BeanC(String name, int value) {
        super(name, value);
    }

    public void initCheck() {
        System.out.println("BeanC -> initMethod by @Bean(initMethod = {})");
    }

    public void destroyCheck() {
        System.out.println("BeanC -> destroyMethod by @Bean(destroyMethod = {})");
    }

}
