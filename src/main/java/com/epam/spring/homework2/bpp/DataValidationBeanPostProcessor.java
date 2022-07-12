package com.epam.spring.homework2.bpp;

import com.epam.spring.homework2.beans.BeanMaster;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class DataValidationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanMaster checkedBean) {
            if (checkedBean.getName() != null && checkedBean.getValue() > 0) {
                return bean;
            }
            checkedBean.setName("DEFAULT");
            checkedBean.setValue(1);
            System.out.println("Fail to validate bean " + beanName + ". Default values are set.");
        }
        return bean;
    }

}
