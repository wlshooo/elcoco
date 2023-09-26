package com.sku.elcoco.global.handler;



import com.sku.elcoco.global.exception.DuplicatedException;
import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.model.ResponseErrorFormat;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//전역적으로 예외 처리를 담당하는 클래스
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    protected ResponseEntity<ResponseErrorFormat> handleDuplicatedException(DuplicatedException e) {
        log.warn("-------HandleDuplicatedException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_BAD_REQUEST.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorFormat);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseErrorFormat> handleNotFoundException(NotFoundException e) {
        log.warn("-------HandleNotFoundException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_NOT_FOUND.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorFormat);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ResponseErrorFormat> handleRuntimeException(RuntimeException e) {
        log.warn("-------HandleRuntimeException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_BAD_REQUEST.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorFormat);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    protected ResponseEntity<ResponseErrorFormat> handleAuthenticationException(AuthenticationException e) {
//        log.warn("-------HandleAuthenticationException-------", e);
//
//        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
//                .message(e.getMessage())
//                .statusCode(ResponseStatus.FAIL_UNAUTHORIZED.getStatusCode())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseErrorFormat);
//    }
}
