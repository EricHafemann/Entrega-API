package br.com.ctw.apientregas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigInteger;

/**
 *DTO de Response da entidade MotoristaEntity
 * @param id ID do Motorista
 * @param nome Nome do Motorista
 * @param cnh CNH do Motorista
 */
@Builder
public record ResponseMotoristaDto (

        @Schema(description = "ID do Motorista", example = "12")
        BigInteger id,

        @Schema(description = "Nome do Motorista", example = "Murilo Heitor")
        String nome,

        @Schema(description = "CNH do Motorista", example = "111222333")
        String cnh
) {}
