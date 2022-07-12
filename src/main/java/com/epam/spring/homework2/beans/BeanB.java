package com.epam.spring.homework2.beans;

public class BeanB extends BeanMaster {

    public BeanB(String name, int value) {
        super(name, value);
    }

    public void initCheck() {
        System.out.println("BeanB -> initMethod by @Bean(initMethod = {})");
    }

    public void destroyCheck() {
        System.out.println("BeanB -> destroyMethod by @Bean(destroyMethod = {})");
    }

    public void otherInitCheck() {
        System.out.println("BeanB -> otherInitMethod by BeanFactoryPostProcessor");
    }

}
