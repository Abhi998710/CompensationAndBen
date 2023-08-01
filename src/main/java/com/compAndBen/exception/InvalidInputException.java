package com.compAndBen.exception;

public class InvalidInputException extends  Exception{

    //version id to identify the serialization request at important time
    private static final long serialVersionUID = 2921933468684518631L;
    public InvalidInputException(String message)
    {
        super(message);
    }

}