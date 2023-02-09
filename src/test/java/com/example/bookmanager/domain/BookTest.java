package com.example.bookmanager.domain;

import com.example.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void bookTest(){
        Book book = new Book();

        book.setName("jpa 공부");
        book.setAuthorId(1L);
        //book.setPublisherId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

}