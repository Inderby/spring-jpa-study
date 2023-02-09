package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Book;
import com.example.bookmanager.domain.Publisher;
import com.example.bookmanager.domain.Review;
import com.example.bookmanager.domain.User;
import com.example.bookmanager.repository.dto.BookStatus;
import jakarta.persistence.Transient;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired ReviewRepository reviewRepository;

    @Autowired UserRepository userRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Jpa 초격차 패키지");
        //book.setAuthor("패스트 캠퍼스" );

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();
        User user = userRepository.findByEmail("martin@study.com");

        System.out.println("Review : " + user.getReviews());
        System.out.println("Book : " + user.getReviews().get(0).getBook());
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher());
    }

    @Test
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA 공부");

//        bookRepository.save(book);

        Publisher publisher = new Publisher();
        publisher.setName("inderby");
//        publisherRepository.save(publisher);

        book.setPublisher(publisher);
        bookRepository.save(book);

//        publisher.addBook(book);
//        publisherRepository.save(publisher);

        System.out.println("book : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("공부");

        bookRepository.save(book1);

        System.out.println("publishers : " + publisherRepository.findAll());

        Book book2 = bookRepository.findById(1L).get();
        bookRepository.delete(book2);

        System.out.println("book : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());
        Book book3 = bookRepository.findById(1L).get();

        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("book : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());
        System.out.println("book3-publishers : " + bookRepository.findById(1L).get().getPublisher());
    }
    @Test
    void bookRemoveCascadeTest(){
        bookRepository.deleteById(1L);
        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void softDelete(){
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(bookRepository.findById(3L));

        bookRepository.findAllByDeletedFalse().forEach(System.out::println);
        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);
    }
    private void givenBookAndReview() {
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    @Test
    void queryTest(){
        System.out.println(bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual("jpa study", LocalDateTime.now(), LocalDateTime.now()));

        System.out.println("findByNameRecently : " + bookRepository.findByNameRecently(
                "Jpa study",
                LocalDateTime.now(),
                LocalDateTime.now()));

        System.out.println(bookRepository.findBookNameAndCategory());

        bookRepository.findBookNameAndCategory().forEach(b -> {
            System.out.println(b.getName() +  " : "  + b.getCategory());
        });

        bookRepository.findBookNameAndCategory(PageRequest.of(1, 1)).forEach(
                bookNameAndCategory -> System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory())
        );

        bookRepository.findBookNameAndCategory(PageRequest.of(0, 1)).forEach(
                bookNameAndCategory -> System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory())
        );
    }

    @Test
    void nativeQueryTest(){
        bookRepository.findAll().forEach(System.out::println);
        bookRepository.findAllCustom().forEach(System.out::println);

        List<Book> books = bookRepository.findAll();

        for(Book book : books){
            book.setCategory("it study");
        }

        bookRepository.saveAll(books);

        System.out.println(bookRepository.findAll());

        System.out.println("affected rows : " + bookRepository.updateCategories());
        System.out.println(bookRepository.findAllCustom());

        System.out.println(bookRepository.showTables());
    }
    @Test
    void converterTest(){
        bookRepository.findAll().forEach(System.out::println);

        Book book = new Book();

        book.setName("other IT study");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);

        System.out.println(bookRepository.findRawRecord().values());//일반적인 findAll 함수로는 확인 불가능 왜냐면 이미 manage상태에 들어갔기 때문. 그러므로 nativeQuery를 이용하여 확인
    }
    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("공부" );
        return publisherRepository.save(publisher);
    }

    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA study");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    private User givenUser(){
        return userRepository.findByEmail("inderby@study.com");
    }
    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("재밌는책");
        review.setContent("너무너무 재밌고 즐거운책");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }

}