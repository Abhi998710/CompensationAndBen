package com.compAndBen.jwttoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
//this will create the jwttoken for the authentication bean and make the use of it
public class jwtTokenUtility implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    //also set the token validity
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    //claims are the pieces of information that are needed to process things

    public <T> T getClaimsFromToken(String token, Function<Claims,T> claimResolver)
    {

        final Claims claims=getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }
    //first you need to extract all claims from the token
    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getEmailIdFromToken(String token)
    {

        return getClaimsFromToken(token,Claims::getSubject);
    }
    //get Expiration of the token
    public Date getExpiryOfToken(String token)
    {
        return getClaimsFromToken(token,Claims::getExpiration);
    }
    //check whether the token is expired or not
    private Boolean isExpiry(String token)
    {
        Date expiration=getExpiryOfToken(token);
        return expiration.before(new Date());
    }
    //now lets generate the token. The claims are generated
    private String doGenerateToken(Map<String,Object> claims,String subject)
    {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    //you need to pass the token and userdetails to validate the token associated
    //with the user
    public boolean validateToken(String token, UserDetails userDetails)
    {
        final String emailid=getEmailIdFromToken(token);
        if(emailid.equals(userDetails.getUsername())&&!isExpiry(token))
        {
            return true;
        }
        else{
            return  false;
        }


    }




}
