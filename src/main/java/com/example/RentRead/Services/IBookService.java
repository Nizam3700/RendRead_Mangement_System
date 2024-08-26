package com.example.RentRead.Services;

import com.example.RentRead.Exceptions.BookIsAlreadyRentedOutException;
import com.example.RentRead.Exceptions.BookNotFoundException;
import com.example.RentRead.Exceptions.NotAbleToRentException;
import com.example.RentRead.Exceptions.UserNotFoundException;
import com.example.RentRead.Exchange.Request.BookRequest;
import com.example.RentRead.Exchange.Request.RentBookRequest;
import com.example.RentRead.Exchange.Request.Response.RentBookResponse;
import com.example.RentRead.Models.BookStore;

import java.util.List;

public interface IBookService {
    BookStore createBook(BookRequest request);

    public interface IRentalManagementService {
        RentBookResponse rentBook(Long id, RentBookRequest request) throws BookNotFoundException, BookIsAlreadyRentedOutException, NotAbleToRentException, UserNotFoundException;
        RentBookResponse returnBook(Long id, RentBookRequest request) throws UserNotFoundException, BookNotFoundException;
    }    BookStore updateBook(Long id, BookRequest request) throws BookNotFoundException;

    BookStore removeBook(Long id) throws BookNotFoundException;

    List<BookStore> getAllBooks();

    BookStore getBookById(Long id) throws BookNotFoundException;
}
