package com.epam.spring.homework6.service.repository;

import com.epam.spring.homework6.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsById(Integer id);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmailAndIdIsNot(String username, String email, int id);

    void deleteById(Integer id);

}
