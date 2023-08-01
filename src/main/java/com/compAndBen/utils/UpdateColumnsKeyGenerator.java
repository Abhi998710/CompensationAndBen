package com.compAndBen.utils;

import com.example.demo.model.FileRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;

public class UpdateColumnsKeyGenerator implements KeyGenerator
{

    @Autowired
    private Environment env;
    @Autowired
    @Override
    public Object generate(Object target, Method method, Object... params) {

        HttpServletRequest request=(HttpServletRequest)params[0];
        String tokenHeader=request.getHeader("Authorization");
        String token=" ";
        FileRequest request2= (FileRequest)params[1];

        if(tokenHeader!=null && tokenHeader.startsWith("Bearer")){
            token=tokenHeader.substring(7);
            Claims claims= Jwts.parser().setSigningKey(env.getProperty("token.secret.key")).parseClaimsJws(token).getBody();
            Object id=request2.getFileId();
            String claim=claims.getSubject();
            return claim+id;

        }
        return null;
    }
}

//it will be returned to the id that is required for now and make the changes to it a
//and make the impact on it \