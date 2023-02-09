package com.example.bookmanager.domain;

import com.example.bookmanager.domain.converter.BookStatusConverter;
import com.example.bookmanager.domain.listener.Auditable;
import com.example.bookmanager.repository.dto.BookStatus;
import com.example.bookmanager.service.BookService;
import com.sun.source.doctree.AuthorTree;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(value = AuditingEntityListener.class)
@DynamicUpdate //필요한 부분만 update를 함
@Where(clause = "deleted = false") //delete가 false일 경우만 entity가 실행됨
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private boolean deleted;

    //@Convert(converter = BookStatusConverter.class)//변환 클래스를 넣어줌
    private BookStatus status;

    //private Long publisherId;

    @OneToOne(mappedBy = "book") //table을 생성하는 sql에서는 연관 제외. 하지만 1대1 연관 상태는 계속 유지됨 . 주의 : toString에서 순환참조가 걸릴 수 있다
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})//book이 persist,merge될 때 publisher, merge또한 persist되게 만드는 옵션
    @ToString.Exclude
    private Publisher publisher;

    @OneToMany
    @JoinColumn(name = "book_id")
    //@ManyToMany //manytomany 연관관계는 잘 사용하지 않으므로 중간 entity를 사용하여 manytoOne 또는 oneToMany 관계로 변환시킨다.
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
    //private List<Author> authors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }


    /*
    public void addAuthor(Author... author){
        Collections.addAll(this.authors, author);
    }
    */

    /*
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
*/

}
