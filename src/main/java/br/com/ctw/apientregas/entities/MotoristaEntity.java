package br.com.ctw.apientregas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_motorista")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MotoristaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 9)
    private String cnh;

    @OneToMany(mappedBy = "motorista")
    private Set<EntregaEntity> entregas = new HashSet<>();
}
