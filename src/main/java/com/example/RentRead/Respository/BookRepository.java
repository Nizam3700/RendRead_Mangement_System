package com.example.RentRead.Respository;


import com.example.RentRead.Models.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookStore, Long> {
    boolean existsByTitle(String title);

}
