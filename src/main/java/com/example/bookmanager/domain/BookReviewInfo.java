package com.example.bookmanager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false)
    private Book book;
    //private Long book_id;
    private float averageReviewScore;

    private int reviewCount;
}
