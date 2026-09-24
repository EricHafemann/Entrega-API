package br.com.ctw.apientregas.repository;

import br.com.ctw.apientregas.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, BigInteger> {

    Optional<UsuarioEntity> findByUsername (String username);
    boolean existsByUsername (String username);
}
