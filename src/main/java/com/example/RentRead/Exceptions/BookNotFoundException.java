package com.example.RentRead.Exceptions;

public class BookNotFoundException extends  RuntimeException {

    public BookNotFoundException(String message){
        super(message);
    }
}
