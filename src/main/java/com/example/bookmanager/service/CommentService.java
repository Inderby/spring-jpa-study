package com.example.bookmanager.service;

import com.example.bookmanager.domain.Comment;
import com.example.bookmanager.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void init(){
        for(int i = 0; i < 10; i++){
            Comment comment = new Comment();
            comment.setComment("최고에요");

            commentRepository.save(comment);
        }
    }

    @Transactional(readOnly = true) //FlushModeType이 auto에서 manual모드로 바뀌며 dirty check가 일어나지 않는다. 대용량 처리에서 성능적인 장점 발생
    public void updateSomething(){
        List<Comment> comments = commentRepository.findAll(); //transaction 함수 내에서 select query를 시행하는 함수는 자동으로 dirty check를 한다.

        for(Comment comment : comments){
            comment.setComment("별로에요");
            commentRepository.save(comment); //주석처리를 해도 persistence context가 dirty check를 하여 자동 save
        }
    }

    @Transactional
    public void insertSomething(){
        Comment comment = new Comment();
        comment.setComment("이건뭐죠");

        commentRepository.save(comment);
    }
}
