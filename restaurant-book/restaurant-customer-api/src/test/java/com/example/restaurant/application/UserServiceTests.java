package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }
    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";
        
        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWhithExistedEmail() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User user = User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        assertThatThrownBy(() -> {
            userService.registerUser(email, name, password);
        }).isInstanceOf(EmailExistedException.class);

        verify(userRepository, never()).save(any());
    }
}