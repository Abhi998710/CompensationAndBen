package com.compAndBen.jwttoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

//set the values and comvert the json object to string
//mostly they are default method that you need to implement
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //add this to the body map and convert them to writeValue using string representation
        final Map<String,Object > body=new LinkedHashMap<>();
        body.put("message",HttpServletResponse.SC_FORBIDDEN);
        body.put("statusCode",accessDeniedException.getMessage());
        body.put("status", HttpStatus.FORBIDDEN);
        //covnvert this body object into string
        final ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),body);

       



    }
}