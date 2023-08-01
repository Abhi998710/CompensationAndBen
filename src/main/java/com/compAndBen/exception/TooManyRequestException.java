package com.compAndBen.exception;

//this is the type of the runtime exception that is being hit
public class TooManyRequestException extends RuntimeException{
  private static final long serialVersionUID = -3956395922398985026L;

  public TooManyRequestException(String message)
  {
      super(message);
  }

}