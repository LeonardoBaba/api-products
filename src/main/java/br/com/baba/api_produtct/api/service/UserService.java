package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.configuration.security.SecurityConfigurations;
import br.com.baba.api_produtct.api.dto.UserFormDTO;
import br.com.baba.api_produtct.api.dto.UserUpdateDTO;
import br.com.baba.api_produtct.api.exception.NotFoundException;
import br.com.baba.api_produtct.api.model.User;
import br.com.baba.api_produtct.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfigurations securityConfigurations;

    public User createUser(UserFormDTO userFormDTO) {
        var password = securityConfigurations.passwordEncoder().encode(userFormDTO.password());
        var user = new User(userFormDTO, password);
        userRepository.save(user);
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product %d not found.", id)));
    }

    public User updateUser(UserUpdateDTO userUpdateDTO) {
        var user = getUserById(userUpdateDTO.id());
        if (userUpdateDTO.name() != null) {
            user.setName(userUpdateDTO.name());
        }
        if (userUpdateDTO.email() != null) {
            user.setEmail(userUpdateDTO.email());
        }
        if (userUpdateDTO.role() != null) {
            user.setRole(userUpdateDTO.role());
        }
        return user;
    }
}
