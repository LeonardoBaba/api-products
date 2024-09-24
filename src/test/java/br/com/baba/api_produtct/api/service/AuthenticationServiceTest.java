package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldReturnUserDetails() {
        String username = "username";
        UserDetails userDetails = mock(UserDetails.class);
        when(userRepository.findByUsername(username)).thenReturn(userDetails);

        UserDetails result = authenticationService.loadUserByUsername(username);

        assertEquals(userDetails, result);
    }

    @Test
    void shouldThrowException() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(username));
    }

}