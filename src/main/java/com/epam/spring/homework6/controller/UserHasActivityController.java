package com.epam.spring.homework6.controller;

import com.epam.spring.homework6.controller.api.UserHasActivityApi;
import com.epam.spring.homework6.controller.dto.UserHasActivityDto;
import com.epam.spring.homework6.service.UserHasActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserHasActivityController implements UserHasActivityApi {

    private final UserHasActivityService userHasActivityService;

    @Override
    public Page<UserHasActivityDto> getSortedPagedUserHasActivities(int page, int size, String sortBy, String order) {
        log.info("accepted request to get userHasActivities");
        return userHasActivityService.getAll(page, size, sortBy, order);
    }

    @Override
    public UserHasActivityDto requestActivity(UserHasActivityDto userHasActivity) {
        log.info("accepted request to request activity for user:{} and activity:{}",
                userHasActivity.getUser().getUsername(),
                userHasActivity.getActivity().getName());
        return userHasActivityService.request(userHasActivity);
    }

    @Override
    public UserHasActivityDto updateUserHasActivity(UserHasActivityDto userHasActivity) {
        log.info("accepted request to update userHasActivity with id:{}", userHasActivity.getId());
        return userHasActivityService.update(userHasActivity);
    }

    @Override
    public ResponseEntity<Void> deleteUserHasActivity(int userHasActivityId) {
        log.info("accepted request to delete userHasActivity with id:{}", userHasActivityId);
        userHasActivityService.deleteById(userHasActivityId);
        return ResponseEntity.noContent().build();
    }

}
