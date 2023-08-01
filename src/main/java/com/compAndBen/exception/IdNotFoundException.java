package com.compAndBen.exception;

public class IdNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;
    public IdNotFoundException(String message)
    {
        super(message);
    }
}

//check whether the id is found or not
//check for that and create the exception class for the same
//and make the impact in general terms and make things work for yourself