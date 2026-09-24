package br.com.ctw.apientregas.controller;

import br.com.ctw.apientregas.dto.request.CreateUsuarioDto;
import br.com.ctw.apientregas.dto.request.LoginUsuarioDto;
import br.com.ctw.apientregas.dto.response.ResponseLoginDto;
import br.com.ctw.apientregas.dto.response.ResponseUsuarioDto;
import br.com.ctw.apientregas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Auth Controller",
        description = "End Points relacionada as rotas de autentificação do sistema."
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Registra um novo usuário",
            description = "Recebe um CreateUsuarioDto e com base nele adiciona um novo usuário no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Usuário criado com sucesso !",
                    responseCode = "201"

            ),
            @ApiResponse(
                    description = "Username já registrado !",
                    responseCode = "409"

            ),
            @ApiResponse(
                    description = "Dados Inválidos !",
                    responseCode = "400"

            )
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseUsuarioDto> register (@Valid @RequestBody CreateUsuarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    @Operation(
            summary = "Login com um Usuário",
            description = "Recebe um LoginUsuarioDto realiza as validações necessárias e realiza o login com o usuário."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Login realizado criado com sucesso !",
                    responseCode = "200"

            ),
            @ApiResponse(
                    description = "Usuário não encontrado !",
                    responseCode = "404"

            ),
            @ApiResponse(
                    description = "Dados Inválidos !",
                    responseCode = "400"

            )
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login (@Valid @RequestBody LoginUsuarioDto dto)
    {
        return ResponseEntity.ok(authService.login(dto));
    }
}
