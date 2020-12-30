package com.example.demo.exceptions;

public class ProjectNotFoundExceptionResponse {
   private String notFound;

    public ProjectNotFoundExceptionResponse(String notFound) {
        this.notFound = notFound;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }
}
