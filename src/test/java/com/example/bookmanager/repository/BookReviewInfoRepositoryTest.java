package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Book;
import com.example.bookmanager.domain.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);
        bookReviewInfoRepository.save(bookReviewInfo);

        System.out.println(">>> " + bookReviewInfoRepository.findAll());
    }

    @Test
    void crudTest2(){
       givenBook();
       givenBookReviewInfo();


        System.out.println(">>>" + bookReviewInfoRepository.findAll());

        Book result =
                bookReviewInfoRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBook();
        System.out.println(">>>" + result);

        BookReviewInfo bookReviewInfo = bookRepository.findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();
        System.out.println(bookReviewInfo);
    }
    private Book givenBook(){
        Book book = new Book();

        book.setName("jpa study");
        book.setAuthorId(1L);
        //book.setPublisherId(1L);

        return bookRepository.save(book);
    }

    private void givenBookReviewInfo(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);

        bookReviewInfoRepository.save(bookReviewInfo);
    }
}