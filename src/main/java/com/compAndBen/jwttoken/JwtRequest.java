package com.compAndBen.jwttoken;


import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String emailid;
    private String password;
    private static final long serialVersionUID = 5926468583005150707L;
    public String getUsername() {
        return emailid;
    }

    public  JwtRequest(String emailid)
    {
        this.emailid = emailid;
    }

    public void setUsername(String username) {
        this.emailid = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}