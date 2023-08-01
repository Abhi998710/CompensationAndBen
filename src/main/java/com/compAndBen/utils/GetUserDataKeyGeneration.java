package com.compAndBen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetUserDataKeyGeneration implements KeyGenerator {


    @Autowired
    private Environment env;
    @Override
    public Object generate(Object target, Method method, Object... params) {
        String token=" ";
        //it return cache+id as the cache key to carry on further operations
        //get the jwt token
        //extract the first parameter of the httpServlet request
        //first will give the claim and the second will give the fileid
        HttpServletRequest request=(HttpServletRequest)params[0];
        String tokenHeader=request.getHeader("Authorization");
        if(tokenHeader.startsWith("Bearer") && tokenHeader!=null)
        {
             token=tokenHeader.substring(7);
            //now we need to create the claims from this token that we got
            Claims claims= Jwts.parser().setSigningKey(env.getProperty("token.secret.key")).parseClaimsJws(token).getBody();
            String claims1=claims.getSubject();
            //from the second parameter they we will get the fileId
            Map<String,Object> request2=(Map<String,Object>)params[1];
            Object id=request2.get("fileId");
            return  claims1+id;

        }
        return null;

    }
}
//claims+id is the cache key they would be storing for the further procedures now on.