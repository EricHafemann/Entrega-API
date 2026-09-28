package br.com.ctw.apientregas.handler;

import br.com.ctw.apientregas.exception.ErroResponse;
import br.com.ctw.apientregas.exception.MotoristaAlreadyExistsException;
import br.com.ctw.apientregas.exception.NotFoundException;
import br.com.ctw.apientregas.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handlerException (Exception ex, HttpServletRequest request)
    {
        ErroResponse erroResponse = ErroResponse.builder()
                .mensagem("Erro interno do sistema !")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erroResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErroResponse> handlerUserAlreadyExistsException (UserAlreadyExistsException ex, HttpServletRequest request)
    {
        ErroResponse erroResponse = ErroResponse.builder()
                .mensagem(ex.getMessage())
                .code(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(erroResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErroResponse> handlerUsernameNotFoundException (UsernameNotFoundException ex, HttpServletRequest request)
    {
        ErroResponse erroResponse = ErroResponse.builder()
                .mensagem(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(erroResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handlerMethodArgumentNotValidException (MethodArgumentNotValidException ex, HttpServletRequest request)
    {
        ErroResponse erroResponse = ErroResponse.builder()
                .mensagem("Dados Inválidos ! Tente Novamente")
                .code(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erroResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponse> handlerBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ErroResponse erro = ErroResponse.builder()
                .mensagem("Credenciais inválidas !")
                .code(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(MotoristaAlreadyExistsException.class)
    public ResponseEntity<ErroResponse> handlerMotoristaAlreadyExists(MotoristaAlreadyExistsException ex, HttpServletRequest request) {
        ErroResponse erro = ErroResponse.builder()
                .mensagem(ex.getMessage())
                .code(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponse> handlerNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErroResponse erro = ErroResponse.builder()
                .mensagem(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErroResponse erro = ErroResponse.builder()
                .mensagem("Corpo da requisição ausente ou inválido !")
                .code(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
}
