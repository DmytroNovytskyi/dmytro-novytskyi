package com.epam.spring.timekeeper.service.repository.impl;

import com.epam.spring.timekeeper.service.model.Category;
import com.epam.spring.timekeeper.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();
    private int id;

    @Override
    public Category create(Category entity) {
        id++;
        entity.setId(id);
        categories.add(entity);
        log.info("successfully created category with id:{}", entity.getId());
        return entity;
    }

    @Override
    public List<Category> findAll() {
        log.info("successfully read categories");
        return new ArrayList<>(categories);
    }

    @Override
    public Category findById(int id) {
        Category category = categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (category != null) {
            log.info("found category with id:{}", category.getId());
        } else {
            log.info("could not find category with id:{}", id);
        }
        return category;
    }

    @Override
    public Category update(Category entity) {
        Category category = null;
        Optional<Category> categoryOptional = categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
            category.setStatus(entity.getStatus());
            category.setTranslations(entity.getTranslations());
            log.info("successfully updated category with id:{}", entity.getId());
        }
        return category;
    }

    @Override
    public void deleteById(int id) {
        categories.removeIf(c -> c.getId() == id);
        log.info("successfully deleted category with id:{}", id);
    }

}
