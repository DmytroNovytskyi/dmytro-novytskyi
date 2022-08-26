package com.epam.spring.homework6.service.impl;

import com.epam.spring.homework6.controller.dto.UserDto;
import com.epam.spring.homework6.service.exception.UserAlreadyExistsException;
import com.epam.spring.homework6.service.exception.UserNotFoundException;
import com.epam.spring.homework6.service.mapper.UserMapper;
import com.epam.spring.homework6.service.model.User;
import com.epam.spring.homework6.service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.homework6.util.ActivityTestData.ID;
import static com.epam.spring.homework6.util.CommonTestData.*;
import static com.epam.spring.homework6.util.UserTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenPageableData_whenFindAll_thenReturnPageOfUsers() {
        Pageable pageable = createPageable();
        List<User> users = createUserList();
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
        Page<UserDto> expectedPage = userPage.map(UserMapper.INSTANCE::mapUserDto);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserDto> actualPage = userService.getAll(PAGE, SIZE, SORT_BY, ORDER);

        assertThat(actualPage.getSize(), is(SIZE));
        assertThat(actualPage.getPageable(), is(pageable));
        assertThat(actualPage, is(expectedPage));
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void givenUserDtoWithExistingUsernameOrEmail_whenCreate_thenThrowUserAlreadyExistsException() {
        UserDto userDto = createUserDto();

        when(userRepository.existsByUsernameOrEmail(USERNAME, EMAIL)).thenReturn(true);

        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> userService.create(userDto))
                .withMessage("User with this username or email already exists!");
        verify(userRepository, times(1))
                .existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void givenValidUserDto_whenCreate_thenReturnCreatedUserDto() {
        UserDto userDto = createUserDto();
        User user = createUser();

        when(userRepository.save(UserMapper.INSTANCE.mapUser(userDto))).thenReturn(user);

        UserDto actualUserDto = userService.create(userDto);
        UserDto expectedUserDto = createUserDto();
        expectedUserDto.setId(user.getId());

        assertThat(actualUserDto, is(expectedUserDto));
        verify(userRepository, times(1)).save(UserMapper.INSTANCE.mapUser(userDto));
    }

    @Test
    void givenUserDtoWithInvalidId_whenUpdate_thenThrowUserNotFoundException() {
        UserDto userDto = createUserDto();
        userDto.setId(ID);

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.update(userDto))
                .withMessage("User was not found!");
        verify(userRepository, times(1)).findById(ID);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void givenActivityDtoWithDuplicateUsernameOrEmail_whenUpdate_thenThrowUserAlreadyExistsException() {
        UserDto userDto = createUserDto();
        userDto.setId(ID);
        User user = createUser();

        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameOrEmailAndIdIsNot(USERNAME, EMAIL, ID)).thenReturn(true);

        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> userService.update(userDto))
                .withMessage("User with this username or email already exists!");
        verify(userRepository, times(1))
                .existsByUsernameOrEmailAndIdIsNot(USERNAME, EMAIL, ID);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void givenValidUserDto_whenUpdate_thenReturnUpdatedUserDto() {
        UserDto userDto = createUserDto();
        userDto.setId(ID);
        User user = createUser();
        user.setUsername(USERNAME + "old");

        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameOrEmailAndIdIsNot(USERNAME, EMAIL, ID)).thenReturn(false);
        when(userRepository.save(createUser())).thenReturn(createUser());

        UserDto updatedUserDto = userService.update(userDto);

        assertThat(updatedUserDto, is(userDto));
        verify(userRepository, times(1)).save(createUser());
    }

    @Test
    void givenInvalidAUserId_whenDelete_thenThrowUserNotFoundException() {
        when(userRepository.existsById(ID)).thenReturn(false);

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.deleteById(ID))
                .withMessage("User was not found!");

        verify(userRepository, times(1)).existsById(ID);
        verify(userRepository, times(0)).deleteById(ID);
    }

    @Test
    void givenValidUserId_whenDelete_thenRepositoryMethodCall() {
        doNothing().when(userRepository).deleteById(ID);
        when(userRepository.existsById(ID)).thenReturn(true);

        userService.deleteById(ID);

        verify(userRepository, times(1)).deleteById(ID);
    }

}
