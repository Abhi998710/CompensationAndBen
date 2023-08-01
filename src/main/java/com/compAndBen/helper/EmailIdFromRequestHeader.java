package com.compAndBen.helper;

import com.compAndBen.exception.InvalidInputException;
import com.example.demo.jwttoken.jwtTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class EmailIdFromRequestHeader {

    @Autowired
    private jwtTokenUtility jwtTokenUtils;


    //all we need to extract is email id from the header
    public String getEmailIdFromRequestHeader(HttpServletRequest request) throws InvalidInputException {
        String jwtToken=null;
        String emailId=null;
        String tokenHeader=request.getHeader("Authorization");
        if(tokenHeader.startsWith("Bearer") && tokenHeader!=null)
            jwtToken=tokenHeader.substring(7);
        try{

            emailId=jwtTokenUtils.getEmailIdFromToken(jwtToken);


        }
        catch(Exception e)
        {
            throw new InvalidInputException("Unable to extract token from emailid");
        }


        return emailId;
    }


}