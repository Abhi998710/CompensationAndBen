package com.compAndBen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@PropertySource("classpath:security.properties")
@Component
public class RequestKeyGenerator implements KeyGenerator {

    @Autowired
    private Environment env;
    @Override
    public Object generate(Object target, Method method, Object... params) {

        HttpServletRequest request=(HttpServletRequest)params[0];
        String tokenHeader=request.getHeader("Authorization");
        String token=" ";

        if(tokenHeader!=null && tokenHeader.startsWith("Bearer")){
            token=tokenHeader.substring(7);
            Claims claims= Jwts.parser().setSigningKey(env.getProperty("token.secret.key")).parseClaimsJws(token).getBody();
            return claims.getSubject();

        }
        return null;
    }


}