package br.com.ctw.apientregas.service;

import br.com.ctw.apientregas.config.service.JwtService;
import br.com.ctw.apientregas.dto.request.CreateUsuarioDto;
import br.com.ctw.apientregas.dto.request.LoginUsuarioDto;
import br.com.ctw.apientregas.dto.response.ResponseLoginDto;
import br.com.ctw.apientregas.dto.response.ResponseUsuarioDto;
import br.com.ctw.apientregas.entities.enumerated.Role;
import br.com.ctw.apientregas.entities.UsuarioEntity;
import br.com.ctw.apientregas.exception.UserAlreadyExistsException;
import br.com.ctw.apientregas.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JpaUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public ResponseUsuarioDto register (CreateUsuarioDto dto)
    {
        if(usuarioRepository.existsByUsername(dto.username()))
        {
            throw new UserAlreadyExistsException("Usuário com esse username já existente !");
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.ROLE_USER)
                .build();

        usuarioRepository.save(usuario);

        return new ResponseUsuarioDto(
                "Seja Bem Vindo, "+ dto.username()   + " !"
        );
    }

    public ResponseLoginDto login(LoginUsuarioDto dto) {

        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        UsuarioEntity usuario = usuarioRepository.findByUsername(dto.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado !"));

        String token = jwtService.generateToken(usuario);

        return new ResponseLoginDto(token);
    }
}
