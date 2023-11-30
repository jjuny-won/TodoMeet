package com.todomeet.todomeet.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleBaseException(BaseException e) {
        return new ExceptionResponse(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD_ERROR, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ExceptionResponse handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return convert(GlobalErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleRuntimeException(RuntimeException e) {
        return convert(GlobalErrorCode.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(MethodArgumentNotValidException e) {
        String detailMessage = extractMessage(e.getBindingResult().getFieldErrors());
        return new ExceptionResponse(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR.getErrorCode(), detailMessage);
    }

    private String extractMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach((error) -> builder.append(error.getDefaultMessage()));
        return builder.toString();
    }

    private ExceptionResponse convert(ErrorCode e, HttpStatus httpStatus) {
        return new ExceptionResponse(e.getErrorCode(), e.getMessage());
    }
}
