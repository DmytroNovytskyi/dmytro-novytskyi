package com.epam.spring.homework1.other;

import com.epam.spring.homework1.beans.BeanC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtherBeanC {

    @Autowired
    BeanC beanC;

    public OtherBeanC() {
        System.out.println(beanC);
    }

}
