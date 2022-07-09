package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.Category;

import java.util.List;

public interface CategoryRepository {

    Category create(Category entity);

    List<Category> findAll();

    Category findById(int id);

    Category update(Category entity);

    void deleteById(int id);

}
