package com.macv.sprint1.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id){
        super("No se encontró el usuario con id " + id);
    }
}
