package br.com.baba.api_product.api.service;

import br.com.baba.api_product.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetails userDetails;

    @Test
    void shouldReturnUserDetails() {
        String username = anyString();
        when(userRepository.findByUsername(username)).thenReturn(userDetails);

        UserDetails result = authenticationService.loadUserByUsername(username);

        assertEquals(userDetails, result);
    }

    @Test
    void shouldThrowException() {
        String username = anyString();
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(username));
    }

}