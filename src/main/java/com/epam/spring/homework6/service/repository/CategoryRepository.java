package com.epam.spring.homework6.service.repository;

import com.epam.spring.homework6.service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsById(Integer id);

    @Query("select case when count(t)> 0 then true else false end from Translation t where t.name = :name")
    boolean existsTranslation(@Param("name") String name);

    void deleteById(Integer id);

}
