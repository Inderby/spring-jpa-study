package com.example.bookmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Publisher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;



    @OneToMany(orphanRemoval = true)//연관관계가 있는 엔티디가 제거되어 필요 없어진 엔티디가 되면 알아서 삭제됨
    @JoinColumn(name = "publisher_id")
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        this.books.add(book);
    }
}
