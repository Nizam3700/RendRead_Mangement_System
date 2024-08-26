package com.example.RentRead.Services.Impl;


import com.example.RentRead.Exceptions.BookNotFoundException;
import com.example.RentRead.Exchange.Request.BookRequest;
import com.example.RentRead.Models.BookStore;
import com.example.RentRead.Respository.BookRepository;
import com.example.RentRead.Services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public BookStore createBook(BookRequest request) {
        if (bookRepository.existsByTitle(request.getTitle())) {
            return null;
        }
        BookStore newBook = BookStore
                .builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .available(request.getAvailable())
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public BookStore updateBook(Long id, BookRequest request) throws BookNotFoundException {
        BookStore book = getBookById(id);
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getGenre() != null) {
            book.setGenre(request.getGenre());
        }
        return bookRepository.save(book);
    }

    @Override
    public BookStore removeBook(Long id) throws BookNotFoundException {
        BookStore book = getBookById(id);
        bookRepository.delete(book);
        return book;
    }

    @Override
    public List<BookStore> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookStore getBookById(Long id) throws BookNotFoundException {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
    }
}

