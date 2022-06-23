package com.epam.spring.homework1;

import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Pet;
import com.epam.spring.homework1.config.BeansConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        System.out.println("Task 1-9:");
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        context.getBean(Pet.class).printPets();
        System.out.println("Task 10:");
        Cheetah first = context.getBean(Cheetah.class);
        Cheetah second = context.getBean("cheetahSecond", Cheetah.class);
        System.out.printf("%s %b %s", first, first == second, second);
    }

}
