package com.macv.sprint1.exception;

public class SellerNotFoundException extends RuntimeException{

    public SellerNotFoundException(int id){
        super("No se encontrĂ³ el vendedor con id "+ id);
    }
}
