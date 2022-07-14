package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    boolean existsById(Integer id);

    boolean existsByName(String name);

    boolean existsByNameAndIdIsNot(String name, int id);

    void deleteById(Integer id);

}
