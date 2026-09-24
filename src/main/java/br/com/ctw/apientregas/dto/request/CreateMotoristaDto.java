package br.com.ctw.apientregas.dto.request;

import java.math.BigInteger;

public record CreateMotoristaDto(
    
        BigInteger id,
        String nome,
        String cnh

) {}
