package br.com.ctw.apientregas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResponseUsuarioDto (
        @Schema(description = "")
        String mensagem
) {}
