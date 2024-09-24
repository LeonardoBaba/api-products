package br.com.baba.api_produtct.api.model;

import br.com.baba.api_produtct.api.dto.UserFormDTO;
import br.com.baba.api_produtct.api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String username;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RoleEnum role;

    private String email;

    private String password;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public User(UserFormDTO userFormDTO, String password) {
        this.username = userFormDTO.username();
        this.name = userFormDTO.name();
        this.role = userFormDTO.role();
        this.email = userFormDTO.email();
        this.password = password;
        this.creationDate = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
