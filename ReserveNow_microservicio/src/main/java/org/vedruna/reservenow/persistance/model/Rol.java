package org.vedruna.reservenow.persistance.model;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Integer rolId;

    @NotBlank
    @Size(max = 45)
    @Column(name = "rol_name", unique = true, nullable = false, length = 45)
    private String rolName;

    @OneToMany(mappedBy = "userRol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> usersWithThisRol;

    @Override
    public String toString() {
        return "Rol{" +
                "rolId=" + rolId +
                ", rolName='" + rolName + '\'' +
                '}';
    }
}
