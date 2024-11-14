package br.com.baba.api_product.api.configuration;

import br.com.baba.api_product.api.dto.UserFormDTO;
import br.com.baba.api_product.api.enums.RoleEnum;
import br.com.baba.api_product.api.repository.UserRepository;
import br.com.baba.api_product.api.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initializeDefaultUser() {
        if (userRepository.findAll().isEmpty()) {
            userService.createUser(new UserFormDTO(
                    "Admin",
                    "Admin",
                    RoleEnum.ADMIN,
                    "admin@admin.com",
                    "123456"
            ));
        }
    }
}
