package com.es.reportverse.repository;

import com.es.reportverse.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericUsuarioRepository<T extends Usuario> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);

}
