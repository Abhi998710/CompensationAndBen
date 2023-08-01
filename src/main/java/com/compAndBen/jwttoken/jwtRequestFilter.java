package com.compAndBen.jwttoken;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class jwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private jwtAuthenticationService jwtUserService;

    @Autowired
    private jwtTokenUtility tokenUtility;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader=request.getHeader("Authorization");
        //initialize the values
        String username=null;
        String jwtToken=null;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer"))
        {
            jwtToken=requestTokenHeader.substring(7);
            try{
                 username=tokenUtility.getEmailIdFromToken(jwtToken);
            }catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        //if this is the first user to be authenticated
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            //userDetails has username and the password which is required to be authenticated
            UserDetails userDetails=jwtUserService.loadUserByUsername(username);
            if(tokenUtility.validateToken(jwtToken,userDetails))
            {
                UsernamePasswordAuthenticationToken userAuthtoken= new UsernamePasswordAuthenticationToken(username,
                        null,userDetails.getAuthorities());
                //password is set to null as the user is already authenticated
                //stores the details such as ip information from request httpservlet request
                userAuthtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //The details property is typically used to store additional information about the authentication request, such as the IP address of the client making the request
                //set the security context in the current config and make the changes
                //in the application to chnage the things accordingly
                SecurityContextHolder.getContext().setAuthentication(userAuthtoken);


            }
        }
        filterChain.doFilter(request,response);
        }
        //SecuritnyContextHolder.getContext().getAuthentication() retrieves the currently authenticated user's authentication object,

        //this is the first request
        //usernamePasswordAuthenticationToken is the concrete implementation of authentication class


        //When chain.doFilter(request, response) is called, the next filter in the chain is invoked,
        //the next filter in the chain is called to process the httpRequest

    }