package br.com.ctw.apientregas.service;

import br.com.ctw.apientregas.dto.request.CreateMotoristaDto;
import br.com.ctw.apientregas.dto.response.ResponseMotoristaDto;
import br.com.ctw.apientregas.entities.MotoristaEntity;
import br.com.ctw.apientregas.exception.MotoristaAlreadyExistsException;
import br.com.ctw.apientregas.exception.NotFoundException;
import br.com.ctw.apientregas.repository.JpaMotoristaRepository;
import br.com.ctw.apientregas.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class MotoristaService
{

    private final JpaMotoristaRepository motoristaRepository;

    public ResponseMotoristaDto create (CreateMotoristaDto dto)
    {
        if(motoristaRepository.existsByCnh(dto.cnh()))
        {
            throw new MotoristaAlreadyExistsException("Motorista com essa CNH já está registrado !");
        }

        MotoristaEntity motorista = MotoristaEntity.builder()
                .id(dto.id())
                .nome(dto.nome())
                .cnh(dto.cnh())
                .build();

        motoristaRepository.save(motorista);

        return ResponseMotoristaDto.builder()
                .nome(dto.nome())
                .cnh(dto.cnh())
                .build();
    }

    public void delete (BigInteger id)
    {
        if(!motoristaRepository.existsById(id))
        {
            throw new NotFoundException("Motorista não foi encontrado !");
        }

        motoristaRepository.deleteById(id);
    }
}
