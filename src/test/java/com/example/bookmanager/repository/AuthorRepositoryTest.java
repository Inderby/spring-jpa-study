package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Author;
import com.example.bookmanager.domain.Book;
import com.example.bookmanager.domain.BookAndAuthor;
import com.sun.source.tree.LambdaExpressionTree;
import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;
    @Test
    @Transactional
    void manyToManyTest(){
        Book book1 = givenBook("book1");
        Book book2 = givenBook("book2");
        Book book3 = givenBook("book3");
        Book book4 = givenBook("book4");

        Author author = givenAuthor("author1");
        Author author1 = givenAuthor("author2");

        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book1, author);
        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book2 , author1);
        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book3, author);
        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book3, author1);
        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book4, author);
        BookAndAuthor bookAndAuthor6 = givenBookAndAuthor(book4, author1);

        /*
        book1.addAuthor(author1);
        book2.addAuthor(author);;
        book3.addAuthor(author, author1);
        book4.addAuthor(author, author1);

        author.addBook(book1, book3, book4);
        author1.addBook(book2, book3, book4);
        */
        book1.addBookAndAuthors(bookAndAuthor1);
        book2.addBookAndAuthors(bookAndAuthor2);
        book3.addBookAndAuthors(bookAndAuthor3,bookAndAuthor4);
        book4.addBookAndAuthors(bookAndAuthor5, bookAndAuthor6);

        author.addBookAndAuthors(bookAndAuthor1, bookAndAuthor3, bookAndAuthor5);
        author1.addBookAndAuthors(bookAndAuthor2, bookAndAuthor4, bookAndAuthor6);
        bookRepository.saveAll(Lists.newArrayList(book1,book2, book3, book4));
        authorRepository.saveAll(Lists.newArrayList(author, author1));

        //System.out.println("authors through book : " + bookRepository.findAll().get(2).getAuthors());
        //System.out.println("books throught author : " + authorRepository.findAll().get(0).getBooks());
        bookRepository.findAll().get(2).getBookAndAuthors().forEach(o -> System.out.println(o.getAuthor()));
        authorRepository.findAll().get(0).getBookAndAuthors().forEach(o-> System.out.println(o.getBook()));
    }

    private Book givenBook(String name){
        Book book = new Book();
        book.setName(name);

        return bookRepository.save(book);
    }

    private Author givenAuthor(String name){
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    private BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = new BookAndAuthor();
        bookAndAuthor.setBook(book);
        bookAndAuthor.setAuthor(author);

        return bookAndAuthorRepository.save(bookAndAuthor);
    }
}