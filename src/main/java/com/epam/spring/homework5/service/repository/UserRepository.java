package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsById(Integer id);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmailAndIdIsNot(String username, String email, int id);

    void deleteById(Integer id);

}
