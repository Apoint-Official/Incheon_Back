package com.apoint.incheon.controller;

import com.apoint.incheon.dto.ErrorDTO;
import com.apoint.incheon.exception.CodeException;
import com.apoint.incheon.exception.IdTokenException;
import com.apoint.incheon.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    //400
    @ExceptionHandler({
            CodeException.class,
            IdTokenException.class
    })
    public ResponseEntity<ErrorDTO> InvalidRequest(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorDTO.builder()
                        .status(400)
                        .message(ex.getMessage())
                        .build()
        );
    }

    //401
    @ExceptionHandler({
            LoginException.class
    })
    public ResponseEntity<ErrorDTO> AuthException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorDTO.builder()
                        .status(401)
                        .message(ex.getMessage())
                        .build()
        );
    }

    //500
    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<ErrorDTO> HandleAllException(final Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorDTO.builder()
                        .status(500)
                        .message(ex.getMessage())
                        .build()
        );
    }
}
