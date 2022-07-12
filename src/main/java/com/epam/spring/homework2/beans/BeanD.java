package com.epam.spring.homework2.beans;

public class BeanD extends BeanMaster {

    public BeanD(String name, int value) {
        super(name, value);
    }

    public void initCheck() {
        System.out.println("BeanD -> initMethod by @Bean(initMethod = {})");
    }

    public void destroyCheck() {
        System.out.println("BeanD -> destroyMethod by @Bean(destroyMethod = {})");
    }

}
