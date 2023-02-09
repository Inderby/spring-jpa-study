package com.example.bookmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Author extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String country;

    @OneToMany
    @JoinColumn(name = "author_id")
    //@ManyToMany //ManyToMany 관계에서는 중간 테이블 생성을 피할 수 없다.
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
    //private List<Book> books = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }
    /*
    public void addBook(Book... book){
        Collections.addAll(this.books, book);
    }*/
}
