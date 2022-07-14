package com.epam.spring.homework5.service.repository;

import com.epam.spring.homework5.service.model.Activity;
import com.epam.spring.homework5.service.model.User;
import com.epam.spring.homework5.service.model.UserHasActivity;
import com.epam.spring.homework5.service.model.enums.UserHasActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasActivityRepository extends JpaRepository<UserHasActivity, Integer> {

    boolean existsByUserAndActivityAndStatus(User user, Activity activity, UserHasActivityStatus status);

    boolean existsByUserAndActivityAndStatusAndIdIsNot(User user, Activity activity,
                                                       UserHasActivityStatus status, Integer id);

    void deleteById(Integer id);

}
