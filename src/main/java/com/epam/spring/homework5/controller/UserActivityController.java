package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.controller.api.UserActivityApi;
import com.epam.spring.homework5.controller.dto.UserActivityDto;
import com.epam.spring.homework5.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserActivityController implements UserActivityApi {

    private final UserActivityService userActivityService;

    @Override
    public Page<UserActivityDto> getSortedPagedUserActivities(int page, int size, String sortBy, String order) {
        log.info("accepted request to get userActivities");
        return userActivityService.getAll(page, size, sortBy, order);
    }

    @Override
    public UserActivityDto requestActivity(UserActivityDto userActivity) {
        log.info("accepted request to request activity for user:{} and activity:{}",
                userActivity.getUser().getUsername(),
                userActivity.getActivity().getName());
        return userActivityService.request(userActivity);
    }

    @Override
    public UserActivityDto updateUserActivity(UserActivityDto userActivity) {
        log.info("accepted request to update userActivity with id:{}", userActivity.getId());
        return userActivityService.update(userActivity);
    }

    @Override
    public ResponseEntity<Void> deleteUserActivity(int userActivityId) {
        log.info("accepted request to delete userActivity with id:{}", userActivityId);
        userActivityService.deleteById(userActivityId);
        return ResponseEntity.noContent().build();
    }

}
