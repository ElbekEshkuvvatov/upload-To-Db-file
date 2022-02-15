package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private  String message;   // xabar
    private  boolean success;   // xabar holati
    private  Object object;   //content

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getObject() {
        return object;
    }
}
