package br.com.ctw.apientregas.exception;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO de resposta, usado cado uma exceção seja lançada
 * @param mensagem
 * @param code
 * @param path
 * @param dateTime
 */
@Builder
public record ErroResponse(
        String mensagem,
        Integer code,
        String path,
        LocalDateTime dateTime
) {
}
