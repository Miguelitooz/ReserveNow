package org.vedruna.reservenow.persistance.model;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotBlank(message = "Username null o vacio")
    @Size(min = 3, max = 20, message = "Longitud incorrecta, tiene que ser entre 3 y 20 caracteres")
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @NotBlank(message = "Contraseña es null o vacio")
    @Size(min = 8, message = "Longitud incorrecta, tiene que tener de maximo 8 caracteres")
    @Column(name = "password", nullable = false, columnDefinition = "CHAR(255)")
    private String password;

    @NotBlank(message = "Email es null")
    @Email(message = "Email en formato incorrecto")
    @Size(max = 90, message = "Longitud incorrecta, tiene que tener de maximo 90 caracteres")
    @Column(name = "email", unique = true, nullable = false, length = 90)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    private Rol userRol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + (userRol != null ? userRol.getRolName() : "No Role") +
                '}';
    }
}
