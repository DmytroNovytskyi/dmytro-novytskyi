package com.epam.spring.homework4.controller;

import com.epam.spring.homework4.controller.api.UserHasActivityApi;
import com.epam.spring.homework4.controller.dto.UserHasActivityDto;
import com.epam.spring.homework4.service.UserHasActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserHasActivityController implements UserHasActivityApi {

    private final UserHasActivityService userHasActivityService;

    @Override
    public List<UserHasActivityDto> getAllUserHasActivities() {
        log.info("accepted request to get all userHasActivities");
        return userHasActivityService.getAll();
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
