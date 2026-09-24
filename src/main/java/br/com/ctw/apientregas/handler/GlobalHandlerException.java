package br.com.ctw.apientregas.handler;

import br.com.ctw.apientregas.exception.ErroResponse;
import br.com.ctw.apientregas.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    
}
