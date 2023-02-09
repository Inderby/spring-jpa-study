package com.example.bookmanager.service;

import com.example.bookmanager.domain.User;
import com.example.bookmanager.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
//    @Autowired
//    private UserRepository userRepository;

    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@study.com");//아직 비영속(new)상태
//        userRepository.save(user);
        entityManager.persist(user);//persistence context로 관리 시작
        entityManager.detach(user);//준영속상태로 변경

        user.setName("newUserAfterPersist"); //별도의 save를 하지 않아도 db에 반영
        entityManager.merge(user); //데이터를 db에 반영

        //entityManager.clear(); //위에 선언한 merge가 무시되고 준영속상태로 변경

        //entityManager.remove(user);//삭제를 해버린 것이기 때문에 merge를 다신 할 수 없음
    }
}
