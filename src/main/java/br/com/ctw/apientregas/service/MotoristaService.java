package br.com.ctw.apientregas.service;

import br.com.ctw.apientregas.dto.request.CreateMotoristaDto;
import br.com.ctw.apientregas.dto.response.ResponseMotoristaDto;
import br.com.ctw.apientregas.entities.MotoristaEntity;
import br.com.ctw.apientregas.exception.MotoristaAlreadyExistsException;
import br.com.ctw.apientregas.exception.NotFoundException;
import br.com.ctw.apientregas.mapper.MotoristaMapper;
import br.com.ctw.apientregas.repository.JpaMotoristaRepository;
import br.com.ctw.apientregas.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MotoristaService
{

    private final JpaMotoristaRepository motoristaRepository;
    private final MotoristaMapper motoristaMapper;

    public ResponseMotoristaDto create (CreateMotoristaDto dto)
    {
        if(motoristaRepository.existsByCnh(dto.cnh()))
        {
            throw new MotoristaAlreadyExistsException("Motorista com essa CNH já está registrado !");
        }

        MotoristaEntity motorista = MotoristaEntity.builder()
                .nome(dto.nome())
                .cnh(dto.cnh())
                .build();

        motoristaRepository.save(motorista);

        return motoristaMapper.toResponse(motorista);
    }

    public ResponseMotoristaDto findById (BigInteger id)
    {
        MotoristaEntity motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Motorista não foi encontrado com esse ID !"));

        return motoristaMapper.toResponse(motorista);
    }

    public List<ResponseMotoristaDto> findByNome(String nome)
    {
        List<MotoristaEntity> motoristas = motoristaRepository.findByNome(nome);

        return motoristaMapper.toListResponse(motoristas);
    }

    public List<ResponseMotoristaDto> findAll ()
    {
        return motoristaMapper.toListResponse(motoristaRepository.findAll());
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
