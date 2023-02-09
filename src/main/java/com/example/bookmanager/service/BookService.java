package com.example.bookmanager.service;

import com.example.bookmanager.domain.Author;
import com.example.bookmanager.domain.Book;
import com.example.bookmanager.repository.AuthorRepository;
import com.example.bookmanager.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Temporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired AuthorService authorService;
    @PersistenceContext
    private EntityManager entityManager;


    public void put(){
        this.putBookAndAuthor(); //bean클래스에서 내부를 호출할 경우 annotation을 읽지 않기 때문에 transactional 어노테이션이 무시될 수 있다.
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) //Exception이 발생할 경우 ro\llback을 한다는 의미 // Propagation.REQUIRES_NEW는  별도의 트랜잭션끼리 동작한다는 뜻
    //NESTED는 상위 트랜잭션에 영향을 주지 않지만 종속적인 속성이다.
     void putBookAndAuthor() //throws Exception{
    {
        Book book = new Book();
        book.setName("JPA 공부");

        bookRepository.save(book);

        authorService.putAuthor();

        throw new RuntimeException("오류가 발생하였습니다."); //unchecked exception의 경우 Db에 commit을 하지 않고 마무리 됨
     //   throw new Exception(); //checked exception의 경우 db에 auto commit후에 마무리 됨. 대신 개발자가 모든 책임을 지게 됨
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ) //repeatable_read수준의 격리 상태에서는 phantom read라는 문제가 발생할 수 있음
    public void get(Long id){
        System.out.println(">>>" + bookRepository.findById(id));
        System.out.println(">>>" + bookRepository.findAll());

        entityManager.clear();

        System.out.println(">>>" + bookRepository.findById(id));//read_committed 격리 단계 수준에서는 다른 transaction에서 commit을 했다면 값이 변경되어 조회될 수 있다.
        System.out.println(">>>" + bookRepository.findAll());

        bookRepository.update();
        Book book = bookRepository.findById(id).get();//dirty read를 할 가능성이 생김
        book.setName("바뀔까?");
        bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAll(){
        List<Book> books = bookRepository.findAll();

        books.forEach(System.out::println);

        return books;
    }
}
