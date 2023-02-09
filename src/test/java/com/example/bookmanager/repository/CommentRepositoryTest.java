package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Transactional
    void commentTest(){
        Comment comment = new Comment();
        comment.setComment("별로에요");
//        comment.setCommentedAt(LocalDateTime.now());

        commentRepository.save(comment);
//        entityManager.clear();
        System.out.println(commentRepository.findById(4L).get());
    }
}