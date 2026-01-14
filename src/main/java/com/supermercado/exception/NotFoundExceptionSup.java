package com.supermercado.exception;

public class NotFoundExceptionSup extends RuntimeException{
    public NotFoundExceptionSup(String mensaje){
        super(mensaje);
    }
}
