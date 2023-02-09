package com.example.bookmanager.service;

import com.example.bookmanager.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void commentTest(){

    }

}