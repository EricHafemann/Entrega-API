package br.com.ctw.apientregas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de criação de um novo motorista.
 * @param nome Nome do Motorista
 * @param cnh CNH do Motorista
 */
public record CreateMotoristaDto(

        @NotBlank(message = "Nome do Motorista não pode ser nulo")
        @Schema(description = "Nome do Motorista", example = "Roberto Alves")
        String nome,

        @NotBlank(message = "CNH do Motorista não pode ser nula")
        @Size(min = 9, max = 9, message = "CNH precisa conter 9 digitos.")
        @Schema(description = "CNH do Motorista", example = "123123456")
        String cnh

) {}
