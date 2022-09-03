package com.epam.spring.homework6;

import com.epam.spring.homework6.controller.dto.*;
import com.epam.spring.homework6.util.PaginatedResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static com.epam.spring.homework6.util.ActivityTestData.createActivityDto;
import static com.epam.spring.homework6.util.CategoryTestData.createCategoryDto;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static com.epam.spring.homework6.util.UserActivityTestData.createUserActivityDto;
import static com.epam.spring.homework6.util.UserTestData.PASSWORD;
import static com.epam.spring.homework6.util.UserTestData.createUserDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SpringTimekeeperApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("http://localhost:${local.server.port}/api/v1/")
    private String baseUrl;

    private final String paginationParameters = "?page=" + PAGE + "&size=" + SIZE + "&sortBy=" + SORT_BY + "&order=" + ORDER;

    @Test
    void givenPageableData_whenGetSortedPagedActivities_thenReturnPageOfActivities() {
        ResponseEntity<PaginatedResponse<ActivityDto>> response = restTemplate
                .exchange(baseUrl + "activity" + paginationParameters,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        Page<ActivityDto> activityDtoPage = response.getBody();
        List<String> names = Objects.requireNonNull(activityDtoPage).getContent().stream()
                .map(ActivityDto::getName)
                .filter(n -> n.equals("activity1"))
                .toList();

        assertThat(activityDtoPage, notNullValue());
        assertThat(activityDtoPage.getSize(), is(SIZE));
        assertThat(activityDtoPage.getNumber(), is(PAGE));
        assertThat(names, containsInAnyOrder("activity1"));
    }

    @Test
    void givenPageableData_whenGetSortedPagedCategories_thenReturnPageOfCategories() {
        ResponseEntity<PaginatedResponse<CategoryDto>> response = restTemplate
                .exchange(baseUrl + "category" + paginationParameters,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        Page<CategoryDto> categoryDtoPage = response.getBody();
        List<String> names = Objects.requireNonNull(categoryDtoPage).getContent().stream()
                .map(CategoryDto::getTranslations)
                .map(l -> {
                    l.removeIf(t -> !t.getLang().equals("en"));
                    return l.get(0).getName();
                })
                .filter(n -> n.equals("category1"))
                .toList();

        assertThat(categoryDtoPage, notNullValue());
        assertThat(categoryDtoPage.getSize(), is(SIZE));
        assertThat(categoryDtoPage.getNumber(), is(PAGE));
        assertThat(names, containsInAnyOrder("category1"));
    }

    @Test
    void givenPageableData_whenGetSortedPagedUsers_thenReturnPageOfUsers() {
        ResponseEntity<PaginatedResponse<UserDto>> response = restTemplate
                .exchange(baseUrl + "user" + paginationParameters,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        Page<UserDto> userDtoPage = response.getBody();
        List<String> names = Objects.requireNonNull(userDtoPage).getContent().stream()
                .map(UserDto::getUsername)
                .filter(n -> n.equals("username1"))
                .toList();

        assertThat(userDtoPage, notNullValue());
        assertThat(userDtoPage.getSize(), is(SIZE));
        assertThat(userDtoPage.getNumber(), is(PAGE));
        assertThat(names, containsInAnyOrder("username1"));
    }

    @Test
    void givenPageableData_whenGetSortedPagedUserActivities_thenReturnPageOfUserActivities() {
        ResponseEntity<PaginatedResponse<UserActivityDto>> response = restTemplate
                .exchange(baseUrl + "user-activity" + paginationParameters,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        Page<UserActivityDto> userActivityDtoPage = response.getBody();
        List<String> usernames = Objects.requireNonNull(userActivityDtoPage).getContent().stream()
                .map(u -> u.getUser().getUsername())
                .filter(n -> n.equals("username1"))
                .distinct()
                .toList();
        List<String> activityNames = Objects.requireNonNull(userActivityDtoPage).getContent().stream()
                .map(u -> u.getActivity().getName())
                .filter(n -> n.equals("activity1"))
                .distinct()
                .toList();

        assertThat(userActivityDtoPage, notNullValue());
        assertThat(userActivityDtoPage.getSize(), is(SIZE));
        assertThat(userActivityDtoPage.getNumber(), is(PAGE));
        assertThat(usernames, containsInAnyOrder("username1"));
        assertThat(activityNames, containsInAnyOrder("activity1"));
    }

    @Test
    void givenValidActivityDto_whenCreateActivity_thenReturnCreatedActivityDto() {
        ActivityDto activityDto = createActivityDto();
        activityDto.setUserCount(null);
        activityDto.getCategory().setId(1001);

        ResponseEntity<ActivityDto> response = restTemplate
                .postForEntity(baseUrl + "activity", activityDto, ActivityDto.class);
        ActivityDto returnedActivityDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(returnedActivityDto, notNullValue());
        assertThat(returnedActivityDto.getId(), notNullValue());
    }

    @Test
    void givenValidCategoryDto_whenCreateCategory_thenReturnCreatedCategoryDto() {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(null);

        ResponseEntity<CategoryDto> response = restTemplate
                .postForEntity(baseUrl + "category", categoryDto, CategoryDto.class);
        CategoryDto returnedCategoryDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(returnedCategoryDto, notNullValue());
        assertThat(returnedCategoryDto.getId(), notNullValue());
    }

    @Test
    void givenValidUserDto_whenCreateUser_thenReturnCreatedUserDto() {
        UserDto userDto = createUserDto();
        userDto.setPassword(PASSWORD);
        userDto.setRepeatPassword(PASSWORD);

        ResponseEntity<UserDto> response = restTemplate
                .postForEntity(baseUrl + "user", userDto, UserDto.class);
        UserDto returnedUserDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(returnedUserDto, notNullValue());
        assertThat(returnedUserDto.getId(), notNullValue());
    }

    @Test
    void givenValidUserActivityDto_whenRequestActivity_thenReturnCreatedUserActivityDto() {
        UserActivityDto userActivityDto = createUserActivityDto();
        userActivityDto.setStatus(null);
        userActivityDto.getUser().setId(1016);
        userActivityDto.getActivity().setId(1013);

        ResponseEntity<UserActivityDto> response = restTemplate
                .postForEntity(baseUrl + "user-activity", userActivityDto, UserActivityDto.class);
        UserActivityDto returnedUserActivityDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(returnedUserActivityDto, notNullValue());
        assertThat(returnedUserActivityDto.getId(), notNullValue());
    }

    @Test
    void givenValidActivityDto_whenUpdateActivity_thenReturnUpdatedActivityDto() {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(1014);
        activityDto.setName("activity2new");
        HttpEntity<ActivityDto> request = new HttpEntity<>(activityDto);

        ResponseEntity<ActivityDto> response = restTemplate
                .exchange(baseUrl + "activity", HttpMethod.PUT, request, ActivityDto.class);
        ActivityDto returnedActivityDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(returnedActivityDto, notNullValue());
        assertThat(returnedActivityDto.getId(), is(1014));
        assertThat(returnedActivityDto.getName(), is("activity2new"));
    }

    @Test
    void givenValidCategoryDto_whenUpdateCategory_thenReturnUpdatedCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1002);
        TranslationDto translationDto = new TranslationDto();
        translationDto.setLang("en");
        translationDto.setName("category2new");
        categoryDto.setTranslations(List.of(translationDto));
        HttpEntity<CategoryDto> request = new HttpEntity<>(categoryDto);

        ResponseEntity<CategoryDto> response = restTemplate
                .exchange(baseUrl + "category", HttpMethod.PUT, request, CategoryDto.class);
        CategoryDto returnedCategoryDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(returnedCategoryDto, notNullValue());
        assertThat(returnedCategoryDto.getId(), is(1002));
        assertThat(returnedCategoryDto.getTranslations().get(0).getName(), is("category2new"));
    }

    @Test
    void givenValidUserDto_whenUpdateUser_thenReturnUpdatedUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1017);
        userDto.setStatus("BANNED");
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);

        ResponseEntity<UserDto> response = restTemplate
                .exchange(baseUrl + "user", HttpMethod.PUT, request, UserDto.class);
        UserDto returnedUserDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(returnedUserDto, notNullValue());
        assertThat(returnedUserDto.getId(), is(1017));
        assertThat(returnedUserDto.getStatus(), is("BANNED"));
    }

    @Test
    void givenValidUserActivityDto_whenUpdateUserActivity_thenReturnUpdatedUserActivityDto() {
        UserActivityDto userActivityDtoBeforeUpdate = new UserActivityDto();
        userActivityDtoBeforeUpdate.setId(1020);
        userActivityDtoBeforeUpdate.setStatus("ASSIGNED");
        HttpEntity<UserActivityDto> request = new HttpEntity<>(userActivityDtoBeforeUpdate);

        ResponseEntity<UserActivityDto> response = restTemplate
                .exchange(baseUrl + "user-activity", HttpMethod.PUT, request, UserActivityDto.class);
        UserActivityDto returnedUserActivityDto = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(returnedUserActivityDto, notNullValue());
        assertThat(returnedUserActivityDto.getId(), is(1020));
        assertThat(returnedUserActivityDto.getStatus(), is("ASSIGNED"));
    }

    @Test
    void givenActivityId_whenDeleteActivity_thenDeleteAndReturnNothing() {
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + "activity/" + 1015, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

    @Test
    void givenCategoryId_whenDeleteCategory_thenDeleteAndReturnNothing() {
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + "category/" + 1003, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

    @Test
    void givenUserId_whenDeleteUser_thenDeleteAndReturnNothing() {
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + "user/" + 1018, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

    @Test
    void givenUserActivityId_whenDeleteUserActivity_thenDeleteAndReturnNothing() {
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + "user-activity/" + 1021, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

}
