package br.com.ctw.apientregas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de resposta, utilizado ao reaizar o login
 * @param token Token JWT
 */
public record ResponseLoginDto (

        @Schema(description = "Token JWT de autentificação", example = "384y27gdwjiefh320y5hr2ubhi...")
        String token
)
{}


