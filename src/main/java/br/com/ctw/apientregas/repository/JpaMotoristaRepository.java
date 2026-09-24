package br.com.ctw.apientregas.repository;

import br.com.ctw.apientregas.entities.MotoristaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface JpaMotoristaRepository extends JpaRepository<MotoristaEntity, BigInteger> {

    boolean existsByCnh (String cnh);
}
