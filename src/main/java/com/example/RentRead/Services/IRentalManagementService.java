package com.example.RentRead.Services;

import com.example.RentRead.Exceptions.BookIsAlreadyRentedOutException;
import com.example.RentRead.Exceptions.BookNotFoundException;
import com.example.RentRead.Exceptions.NotAbleToRentException;
import com.example.RentRead.Exceptions.UserNotFoundException;
import com.example.RentRead.Exchange.Request.RentBookRequest;
import com.example.RentRead.Exchange.Request.Response.RentBookResponse;

public interface IRentalManagementService {
    RentBookResponse rentBook(Long id, RentBookRequest request) throws BookNotFoundException, BookIsAlreadyRentedOutException, NotAbleToRentException, UserNotFoundException;
    RentBookResponse returnBook(Long id, RentBookRequest request) throws UserNotFoundException, BookNotFoundException;
}
