package com.compAndBen.utils;

import com.example.demo.model.FileRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@PropertySource("classpath=security.properties")
public class UploadUserFileKey implements KeyGenerator {

    @Autowired
    private Environment env;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        HttpServletRequest request=(HttpServletRequest)params[0];
        String tokenHeader=request.getHeader("Authorization");
        String token=" ";
        Object id=params[2];

        if(tokenHeader!=null && tokenHeader.startsWith("Bearer")){
            token=tokenHeader.substring(7);
            Claims claims= Jwts.parser().setSigningKey(env.getProperty("token.secret.key")).parseClaimsJws(token).getBody();
            String claim=claims.getSubject();
            return claim+id;

        }
        return null;
    }
}