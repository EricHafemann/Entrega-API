package br.com.ctw.apientregas.repository;

import br.com.ctw.apientregas.entities.MotoristaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface JpaMotoristaRepository extends JpaRepository<MotoristaEntity, BigInteger> {

    boolean existsByCnh (String cnh);

    @Query("SELECT m FROM MotoristaEntity m WHERE m.nome LIKE %:nome%")
    List<MotoristaEntity> buscarPorNome(@Param("nome") String nome);
}
