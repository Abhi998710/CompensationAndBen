package com.compAndBen.jwttoken;

import java.io.Serializable;

public class jwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    //we cannot set the token we can only get the token
    private final String jwttoken;

    public jwtResponse(String jwttoken)
    {
        this.jwttoken=jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}