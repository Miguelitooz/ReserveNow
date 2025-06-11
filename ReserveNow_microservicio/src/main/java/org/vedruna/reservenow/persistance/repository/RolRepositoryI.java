    package org.vedruna.reservenow.persistance.repository;
    import java.util.Optional;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import org.vedruna.reservenow.persistance.model.Rol;

    @Repository
    public interface RolRepositoryI extends JpaRepository<Rol, Integer> {
        //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
        Optional<Rol> findByRolName(String rolName);
    } 
