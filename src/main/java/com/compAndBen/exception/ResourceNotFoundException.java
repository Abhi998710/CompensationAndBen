package com.compAndBen.exception;

public class ResourceNotFoundException extends Exception {


    private static final long serialVersionUID = 4901938508216288263L;
    public ResourceNotFoundException(String message)
    {
        super(message);

    }
}