package com.compAndBen.jwttoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
//handled all exception in auth entry point by interceptio
@Component
public class jwtAuthEntry implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //here we have two kinds on exceptions to handle
        //if the exception is null it is basically badrequest
        //set the values in the response
        //server will understand it but will refuse to authorize the request

        //also need to specify the json value after the request comes to the place
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //get the exception class attribute from the request
        Exception exception= (Exception) request.getAttribute("Exception");


        //create the hashMap body to store the values

        HashMap<String, Object> body=new HashMap<>();
        String message="message";
        String statusCode="statusCode";
        String status="status";


        //then give the filter for the exception value
        try{
            String tokenheader=request.getHeader("Authorization");
            String token=tokenheader.substring(7);
            if(exception!=null)
            {
                response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
                //put everything in the body
                body.put(statusCode,HttpServletResponse.SC_GATEWAY_TIMEOUT);
                body.put(status, HttpStatus.GATEWAY_TIMEOUT);
                body.put(message,"Jwt token got expired");
                //this should be the final attribute
                final ObjectMapper mapper=new ObjectMapper();
                mapper.writeValue(response.getOutputStream(),body);
            }
            else
            {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                body.put(statusCode,HttpServletResponse.SC_BAD_REQUEST);
                body.put(status, HttpStatus.BAD_REQUEST);
                body.put(message,"Jwt token not found ");
                final ObjectMapper mapper=new ObjectMapper();
                mapper.writeValue(response.getOutputStream(),body);

            }

        }
        catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            body.put(statusCode,HttpServletResponse.SC_NOT_FOUND);
            body.put(status, HttpStatus.NOT_FOUND);
            body.put(message,"Jwt token not found ");
            final ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(response.getOutputStream(),body);




        }
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized");
    }
}
//this will give exception for unauthorized and tell to give the authorization
//information once the request reaches the server