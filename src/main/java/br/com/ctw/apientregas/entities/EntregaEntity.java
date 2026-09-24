package br.com.ctw.apientregas.entities;

import br.com.ctw.apientregas.entities.enumerated.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "tb_entrega")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private MotoristaEntity motorista;
}
