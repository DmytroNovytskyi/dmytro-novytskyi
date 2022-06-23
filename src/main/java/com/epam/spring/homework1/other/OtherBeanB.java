package com.epam.spring.homework1.other;

import com.epam.spring.homework1.beans.BeanB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtherBeanB {

    BeanB beanB;

    @Autowired
    public void setBean(BeanB beanB) {
        this.beanB = beanB;
        System.out.println(this.getClass().getSimpleName()
                + ". " + beanB.getClass().getSimpleName()
                + " was injected through the constructor");
    }
}
