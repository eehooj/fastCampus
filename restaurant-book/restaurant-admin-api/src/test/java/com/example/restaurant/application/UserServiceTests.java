package com.example.restaurant.application;

import com.example.restaurant.domain.User;
import com.example.restaurant.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
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
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@exaple.com")
                .name("tester")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);
        List<User> users = userService.getUsers();

        User user = users.get(0);

        assertThat(user.getName()).isEqualTo("tester");
    }

    @Test
    public void addUser() {
        String email = "admin@example.com";
        String name = "Administrator";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    public void updateUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Superman";
        Long level = 100L;

        User mockUser = User.builder().id(id).email(email).name("Adminisrator").level(1L).build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName()).isEqualTo("Superman");
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    public void deactiveUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Adminisrator";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .email("admin@example.com")
                .name("Adminisrator")
                .level(100L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deactiveUser(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin()).isFalse();
        assertThat(user.isActive()).isFalse();
    }
}