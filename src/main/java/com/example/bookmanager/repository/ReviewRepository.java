package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select distinct r from Review r join fetch r.comments") //jpql로 n+1문제 처리 1번째 방법
    List<Review> findAllByFetchJoin();

    @EntityGraph(attributePaths = "comments")//문제 해결 방법2
    @Query("select r from Review r")
    List<Review> findAllByEntityGraph();

    @EntityGraph(attributePaths = "comments")//findall을 overriding으로 해결
    List<Review> findAll();
}
