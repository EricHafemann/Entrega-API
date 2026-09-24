package br.com.ctw.apientregas.controller;

import br.com.ctw.apientregas.dto.request.CreateMotoristaDto;
import br.com.ctw.apientregas.dto.response.ResponseMotoristaDto;
import br.com.ctw.apientregas.service.MotoristaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(
        name = "Motorista Controller",
        description = "End Points relacionado a entidade MotoristaEntity."
)
@RestController
@RequestMapping("/api/motoristas")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<ResponseMotoristaDto> save (CreateMotoristaDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(motoristaService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (
            @PathVariable BigInteger id
    ){
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
