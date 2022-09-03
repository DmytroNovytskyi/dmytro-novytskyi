package com.epam.spring.homework6.service.repository;

import com.epam.spring.homework6.service.model.Activity;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.model.UserActivity;
import com.epam.spring.homework6.service.model.enums.UserActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {

    boolean existsByUserAndActivityAndStatus(User user, Activity activity, UserActivityStatus status);

    boolean existsByUserAndActivityAndStatusAndIdIsNot(User user, Activity activity,
                                                       UserActivityStatus status, Integer id);

    void deleteById(Integer id);

}
