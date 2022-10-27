package com.usv.siriusvoleiapp.exceptions;

public class CrudOperationException extends RuntimeException{
    public CrudOperationException(String mesaj){
        super(mesaj);
    }
}
