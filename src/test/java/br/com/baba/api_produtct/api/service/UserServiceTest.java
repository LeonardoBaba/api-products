package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.dto.UserFormDTO;
import br.com.baba.api_produtct.api.dto.UserUpdateDTO;
import br.com.baba.api_produtct.api.enums.RoleEnum;
import br.com.baba.api_produtct.api.exception.NotFoundException;
import br.com.baba.api_produtct.api.model.User;
import br.com.baba.api_produtct.api.repository.UserRepository;
import br.com.baba.api_produtct.api.security.SecurityConfigurations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfigurations securityConfigurations;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserFormDTO userFormDTO;

    @Mock
    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateUser() {
        this.userFormDTO = new UserFormDTO("username", "name"
                , RoleEnum.MANAGER, "email@email.com", "password");
        String encodedPassword = "encodePassword";
        given(securityConfigurations.passwordEncoder()).willReturn(passwordEncoder);
        given(passwordEncoder.encode(userFormDTO.password())).willReturn(encodedPassword);
        this.user = new User(userFormDTO, encodedPassword);

        userService.createUser(userFormDTO);

        then(userRepository).should().save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getRole(), savedUser.getRole());
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void shouldReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void shouldThrowNotFoundException() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(userId, "newName", "newEmail@email.com", RoleEnum.ADMIN);
        User user = new User();
        user.setId(userId);
        user.setName("oldName");
        user.setEmail("oldEmail");
        user.setRole(RoleEnum.MANAGER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUser(userUpdateDTO);

        assertEquals(userUpdateDTO.name(), updatedUser.getName());
        assertEquals(userUpdateDTO.email(), updatedUser.getEmail());
        assertEquals(userUpdateDTO.role(), updatedUser.getRole());
    }
}
