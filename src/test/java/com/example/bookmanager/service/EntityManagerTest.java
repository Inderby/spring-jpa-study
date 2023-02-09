package com.example.bookmanager.service;

import com.example.bookmanager.domain.User;
import com.example.bookmanager.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class EntityManagerTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Test
    void entityManagerTest(){
        System.out.println(entityManager.createQuery("select u from User u").getResultList());

    }

    @Test
    void cacheFindTest(){
        System.out.println(userRepository.findByEmail("inderby@study.com"));
        System.out.println(userRepository.findByEmail("inderby@study.com"));//1차 cache에서
        System.out.println(userRepository.findByEmail("inderby@study.com"));//1차 cache에서 가져옴
    }


}
