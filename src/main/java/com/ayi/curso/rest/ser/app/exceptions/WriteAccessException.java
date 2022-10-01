package com.ayi.curso.rest.ser.app.exceptions;

public class WriteAccessException extends ReadAccessException{

    public WriteAccessException(String message){
        super(message);
    }
}
