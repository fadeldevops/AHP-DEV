package com.example.learn.api.master.handler;

import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.base.response.ResponseStatus;
import com.example.learn.api.master.entity.MessageValidEntity;
import com.example.learn.api.master.exception.BusinessException;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.MessageValidRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.text.MessageFormat;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MessageValidRepo messageRepo;

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,
            WebRequest request) {
        log.debug("RestResponseEntityExceptionHandler.handleRuntimeException...");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());

        response.setMessageCode("COMMNERR00002");
        response.setMessage(messageService(response.getMessageCode(), ex.getMessage()));

        log.error(response.getMessageCode(), ex);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();

            log.error("Generate response got JsonProcessingException...", ex2);
        }

        return handleExceptionInternal(ex, body, header,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleExceptionGlobal(Exception ex,
            WebRequest request) {
        log.debug("RestResponseEntityExceptionHandler.handleException...");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessageCode("COMMNERR00002");
        response.setMessage(messageService(response.getMessageCode(),
                ex.getMessage()));

        log.error(response.getMessageCode(), ex);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();

            log.error("Generate response got JsonProcessingException...", ex2);
        }

        return handleExceptionInternal(ex, body, header,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        log.debug("RestResponseEntityExceptionHandler.handleValidationException...");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessageCode(ex.getMessageCode());
        response.setMessage(messageService(response.getMessageCode(),
                ex.getVarValues()));

        log.error(response.getMessageCode(), ex);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        log.debug("RestResponseEntityExceptionHandler.handleBusinessException...");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessageCode(ex.getMessageCode());
        response.setMessage(messageService(response.getMessageCode(),
                ex.getVarValues()));

        log.error(response.getMessageCode(), ex);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, HttpStatus.NOT_ACCEPTABLE, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    private String messageService(String msgCd, String... varValues) {

        MessageValidEntity getMessage = messageRepo.findById(msgCd).orElse(null);

        return MessageFormat.format(getMessage.getMsgText(), varValues);
    }

}