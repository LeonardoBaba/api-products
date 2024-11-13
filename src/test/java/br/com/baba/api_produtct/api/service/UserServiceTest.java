package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.configuration.security.SecurityConfigurations;
import br.com.baba.api_produtct.api.dto.UserFormDTO;
import br.com.baba.api_produtct.api.dto.UserUpdateDTO;
import br.com.baba.api_produtct.api.enums.RoleEnum;
import br.com.baba.api_produtct.api.exception.NotFoundException;
import br.com.baba.api_produtct.api.model.User;
import br.com.baba.api_produtct.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfigurations securityConfigurations;

    @Spy
    @InjectMocks
    private UserService userService;

    @Mock
    private UserFormDTO userFormDTO;

    @Mock
    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserUpdateDTO userUpdateDTO;

    @Test
    void shouldSaveUser() {
        String password = "test";
        when(securityConfigurations.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(userFormDTO.password())).thenReturn(password);

        User savedUser = userService.createUser(userFormDTO);

        verify(userRepository, times(1)).save(savedUser);
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.getUserById(anyLong()));
    }

    @Test
    void shouldThrowNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(anyLong()));
    }

    @Test
    void shouldUpdateUserName() {
        String newName = "newName";
        user = new User();
        when(userUpdateDTO.name()).thenReturn(newName);
        doReturn(user).when(userService).getUserById(anyLong());

        User updatedUser = userService.updateUser(userUpdateDTO);

        assertEquals(newName, updatedUser.getName());
    }

    @Test
    void shouldUpdateEmail() {
        String newEmail = "newEmail";
        user = new User();
        when(userUpdateDTO.email()).thenReturn(newEmail);
        doReturn(user).when(userService).getUserById(anyLong());

        User updatedUser = userService.updateUser(userUpdateDTO);

        assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    void shouldUpdateRole() {
        RoleEnum newRole = RoleEnum.MANAGER;
        user = new User();
        when(userUpdateDTO.role()).thenReturn(newRole);
        doReturn(user).when(userService).getUserById(anyLong());

        User updatedUser = userService.updateUser(userUpdateDTO);

        assertEquals(newRole, updatedUser.getRole());
    }

    @Test
    void shouldNotUpdateAnyProperty() {
        doReturn(user).when(userService).getUserById(anyLong());

        User updatedUser = userService.updateUser(userUpdateDTO);

        assertEquals(user, updatedUser);
    }
}
