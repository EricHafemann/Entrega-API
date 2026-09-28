package br.com.ctw.apientregas.controller;

import br.com.ctw.apientregas.dto.request.CreateMotoristaDto;
import br.com.ctw.apientregas.dto.response.ResponseMotoristaDto;
import br.com.ctw.apientregas.service.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Tag(
        name = "Motorista Controller",
        description = "End Points relacionado a entidade MotoristaEntity."
)
@RestController
@RequestMapping("/api/motoristas")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService motoristaService;

    @Operation(
            summary = "Adiciona Motorista",
            description = "Recebe um CreateMotoristaDto, valida suas informações e salva no banco."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Motorista cadastrado com Sucesso.",
                    responseCode = "201"
            ),
            @ApiResponse(
                    description = "Caso o body da requisição seja nulo",
                    responseCode = "400"
            ),
            @ApiResponse(
                    description = "Caso os dados passados estejam inválidos.",
                    responseCode = "400"
            ),
            @ApiResponse(
                    description = "Caso a CNH já esteja registrada",
                    responseCode = "409"
            )

    })
    @PostMapping
    public ResponseEntity<ResponseMotoristaDto> save (@Valid @RequestBody CreateMotoristaDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(motoristaService.create(dto));
    }

    @Operation(
            summary = "Busca Motoristas",
            description = "Retorna todos os motoristas salvos no sistema."
    )
    @ApiResponse(
            description = "Motoristas retornados com sucesso",
            responseCode = "200"
    )
    @GetMapping()
    public ResponseEntity<List<ResponseMotoristaDto>> findAll (){
        return ResponseEntity.status(HttpStatus.OK).body(motoristaService.findAll());
    }

    @Operation(
            summary = "Busca Motorista pelo ID",
            description = "Recebe um ID e busca um motorista com base nele."
    )
    @ApiResponses({
            @ApiResponse(
                description = "Motorista retornado com sucesso",
                responseCode = "200"
            ),
            @ApiResponse(
                    description = "Motorista não encontrado com esse ID",
                    responseCode = "404"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMotoristaDto> findById (@PathVariable BigInteger id){
        return ResponseEntity.status(HttpStatus.OK).body(motoristaService.findById(id));
    }

    @Operation(
            summary = "Busca Motoristas pelo nome",
            description = "Retorna todos os motoristas salvos no sistema com o nome escolhido."
    )
    @ApiResponse(
            description = "Motoristas retornados com sucesso",
            responseCode = "200"
    )
    @GetMapping("/busca/{nome}")
    public ResponseEntity<List<ResponseMotoristaDto>> findByName(@PathVariable String nome) {
        return ResponseEntity.ok(motoristaService.findByNome(nome));
    }

    @Operation(
            summary = "Deleta um  Motorista",
            description = "Recebe um ID e remove o motorista que o possui."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Motoristas removido com sucesso",
                    responseCode = "204"
            ),
            @ApiResponse(
                    description = "Motorista não encontrado com esse ID",
                    responseCode = "404"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable BigInteger id){
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
