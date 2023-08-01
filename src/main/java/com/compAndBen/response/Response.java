package com.compAndBen.response;

import com.fasterxml.jackson.annotation.JsonInclude;

//it is used to include only non null properties for
//serialization, rest null properties will be excluded to
//reduce the length of the output
//this means we can put any type of content in the output
//and that will make the difference while giving the resultant output.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

  //getter and setter are needed to set message,statuscode,status and content
  private String message;
  private String status;
  private int statusCode;
  private T content;

  public Response(String message, String status, int statusCode, T content) {
      this.message = message;
      this.status = status;
      this.statusCode = statusCode;
      this.content = content;
  }

  public Response(String message, String status, int statusCode) {
      this.message=message;
      this.status=status;
      this.statusCode=statusCode;
  }

  @Override
  public String toString() {
      return "Response{" +
              "message='" + message + '\'' +
              ", status='" + status + '\'' +
              ", statusCode=" + statusCode +
              ", content=" + content +
              '}';
  }

  public String getMessage() {
      return message;
  }

  public void setMessage(String message) {
      this.message = message;
  }

  public String getStatus() {
      return status;
  }

  public void setStatus(String status) {
      this.status = status;
  }

  public int getStatusCode() {
      return statusCode;
  }

  public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
  }

  public T getContent() {
      return content;
  }

  public void setContent(T content) {
      this.content = content;
  }
}