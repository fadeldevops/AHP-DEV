package com.example.learn.api.master.security;

import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.base.response.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Response<Object> resp = new Response<>(ResponseStatus.ERROR, new Date());
        resp.setMessageCode("COMMNERR00008");
        resp.setMessage("Bad credentials");

        String body = null;
        try {
            body = objectMapper.writeValueAsString(resp);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();

            log.error("Generate response got JsonProcessingException...", ex2);
        }

        response.getWriter().write(body);
    }
}