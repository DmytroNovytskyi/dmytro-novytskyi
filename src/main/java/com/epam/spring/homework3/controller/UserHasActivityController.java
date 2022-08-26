package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.dto.UserHasActivityDto;
import com.epam.spring.homework3.service.UserHasActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user-has-activity")
public class UserHasActivityController {

    private final UserHasActivityService userHasActivityService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserHasActivityDto> getAllUserHasActivities() {
        log.info("accepted request to get all userHasActivities");
        return userHasActivityService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserHasActivityDto requestActivity(@RequestBody UserHasActivityDto userHasActivity) {
        log.info("accepted request to request activity for user:{} and activity:{}",
                userHasActivity.getUser().getUsername(),
                userHasActivity.getActivity().getName());
        return userHasActivityService.request(userHasActivity);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UserHasActivityDto updateUserHasActivity(@RequestBody UserHasActivityDto userHasActivity) {
        log.info("accepted request to update userHasActivity with id:{}", userHasActivity.getId());
        return userHasActivityService.update(userHasActivity);
    }

    @DeleteMapping("/{userHasActivityId}")
    public ResponseEntity<Void> deleteUserHasActivity(@PathVariable int userHasActivityId) {
        log.info("accepted request to delete userHasActivity with id:{}", userHasActivityId);
        userHasActivityService.deleteById(userHasActivityId);
        return ResponseEntity.noContent().build();
    }

}
