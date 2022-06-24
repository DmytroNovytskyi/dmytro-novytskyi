package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanE extends BeanMaster{

    @PostConstruct
    public void postConstructMethod(){
        System.out.println("BeanE -> postConstructMethod by @PostConstruct");
    }

    @PreDestroy
    public void preDestroyMethod(){
        System.out.println("BeanE -> preDestroyMethod by @PreDestroy");
    }

}
