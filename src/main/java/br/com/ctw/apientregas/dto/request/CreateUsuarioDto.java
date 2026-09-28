package br.com.ctw.apientregas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de criação de um novo usuário.
 * @param username Nome do Usuário
 * @param password Senha do Usuário
 */
public record CreateUsuarioDto(

        @NotBlank(message = "Username é obrigatório.")
        @Size(min = 3, max = 100, message = "Username precisa conter entre 3 caracteres a 100 caracteres.")
        @Schema(description = "Nome do Usuário (Unico)", example = "Gustavo Nogath")
        String username,

        @NotBlank(message = "Senha é obrigatório.")
        @Size(min = 5, message = "Senha precisa conter pelo menos 5 caracteres.")
        @Schema(description = "Senha do Usuário     ", example = "123@Mudar")
        String password
) {
}
