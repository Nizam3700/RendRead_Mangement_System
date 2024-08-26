package com.example.RentRead.Services.Impl;


import com.example.RentRead.Exceptions.BookIsAlreadyRentedOutException;
import com.example.RentRead.Exceptions.BookNotFoundException;
import com.example.RentRead.Exceptions.NotAbleToRentException;
import com.example.RentRead.Exceptions.UserNotFoundException;
import com.example.RentRead.Exchange.Request.RentBookRequest;
import com.example.RentRead.Exchange.Request.Response.RentBookResponse;
import com.example.RentRead.Models.BookStore;
import com.example.RentRead.Models.User;
import com.example.RentRead.Respository.BookRepository;
import com.example.RentRead.Respository.UserRepository;
import com.example.RentRead.Services.IRentalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RentalManagementServiceImpl implements IRentalManagementService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RentBookResponse rentBook(Long id, RentBookRequest request) throws BookNotFoundException, BookIsAlreadyRentedOutException, NotAbleToRentException, UserNotFoundException {
        BookStore book = bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book is not present"));
        User user = getUserWithRentedBooks(request.getUserId());
        if (!book.getAvailable()) {
            throw new BookIsAlreadyRentedOutException("Book is already rented out");
        }
        if (user.getBooks().size() >= 2) {
            throw new NotAbleToRentException(
                    "Not able to rent book, you already rented 2 books"
            );
        }
        book.setAvailable(false);
        List<BookStore> books = user.getBooks();
        books.add(book);
        book.setUser(user);
        user.setBooks(books);
        userRepository.save(user);
        return RentBookResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .books(user.getBooks())
                .role(user.getRole())
                .build();
    }

    @Override
    public RentBookResponse returnBook(Long id, RentBookRequest request) throws UserNotFoundException, BookNotFoundException {
        User user = getUserWithRentedBooks(request.getUserId());
        BookStore book = bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book is not  found"));
        List<BookStore> books = user.getBooks();
        if (!books.contains(book)) {
            throw new BookNotFoundException("Book is not found");
        }
        book.setAvailable(true);
        book.setUser(null);
        books.remove(book);
        user.setBooks(books);
        userRepository.save(user);
        return RentBookResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .books(user.getBooks())
                .role(user.getRole())
                .build();
    }

    private User getUserWithRentedBooks(Long id) throws UserNotFoundException {
        return userRepository
                .findUserWithRentedBooksById(id)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
    }
}

