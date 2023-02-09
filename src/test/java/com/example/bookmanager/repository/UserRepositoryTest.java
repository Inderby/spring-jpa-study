package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Address;
import com.example.bookmanager.domain.Gender;
import com.example.bookmanager.domain.User;
import com.example.bookmanager.domain.UserHistory;
import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Test
    //@Transactional
    void crud(){

        userRepository.save(new User());
        System.out.println(">>> " + userRepository.findAll());

        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name")); //name column을 기준으로 역순으로 정렬한다.
        users.forEach(System.out::println);
        List<User> users = userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L)); //id가 1, 3 ,5 인것을 골라온다.
        users.forEach(System.out::println);

        User user1 = new User("jackson", "sdf@naver.com");
        userRepository.save(user1);

         Optional<User> user = userRepository.findById(1L); // optional 객체로 한번 wrapping되서 반환됨
         User user2 = userRepository.findById(1L).orElse(null);

        userRepository.saveAndFlush(new User("new inderby", "newInderby@naver.com"));

        userRepository.flush();// 쿼리에 변화를 주는 것이 아닌 DB반영 시점을 조절하는 역할

        userRepository.findAll().forEach(System.out::println);

        long count = userRepository.count();

        System.out.println(count);

        boolean exists = userRepository.existsById(1L); //존재 유무 출력

        System.out.println(exists);

        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));

        userRepository.deleteById(1L);

        userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(1L, 3L)));

        userRepository.deleteAllInBatch(userRepository.findAllById(Lists.newArrayList(1L, 3L))); //select로 entity의 존재 유무를 확인하지 않고 쿼리 하나로 바로 다 지워버림

        Page<User> users = userRepository.findAll(PageRequest.of(1, 3)); //zero based indexed 이기 때문에 2(index = 1)페이지를 가져온다는 뜻
        System.out.println("total elements : " + users.getTotalElements());
        System.out.println("total pages : " + users.getTotalPages());
        System.out.println("numberOfElements : " + users.getNumberOfElements());
        System.out.println("sort : " + users.getSort());
        System.out.println("size : " + users.getSize());

        users.getContent().forEach(System.out::println);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name") //name column의 일치성은 무시
                .withMatcher("email", endsWith()); //끝부분이 일치하는 것들 조회해서 가져온다고 설정
        Example<User> example = Example.of(new User("ma", "naver.com"),matcher); //email column의 끝부분이 "naver.com"인 record 들을 가져옴

        userRepository.findAll(example).forEach(System.out::println);


        Example<User> example1 = Example.of(new User("inderby", "inderby@naver.com")); //구체적으로 일치하는 record만 가져옴
        userRepository.findAll(example1).forEach(System.out::println);

        User user = new User();

        user.setEmail("slow");

        ExampleMatcher matcher1 = ExampleMatcher.matching()
                .withMatcher("email", contains()); //양방향 matching을 통해 email column에  slow라는 단어가 포함되어 있는 record를 가져온다.
        Example<User> example2 = Example.of(user, matcher1);
        userRepository.findAll(example2).forEach(System.out::println);

        userRepository.save(new User("david", "david@naver.com"));

        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("davidd@naver.com");
        userRepository.save(user); // id 값을 기준으로 insert query가 아닌 update query가 실행



    }

    @Test
    void select(){

        System.out.println(userRepository.findByName("inderby")); //record의 갯수에 따라 리턴타입을 정해줘야함

        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fastcampos.com"));
        System.out.println("getByEmail : " + userRepository.getByEmail("martin@fastcampos.com"));
        System.out.println("readByEmail : " + userRepository.readByEmail("martin@fastcampos.com"));
        System.out.println("queryByEmail : " + userRepository.queryByEmail("martin@fastcampos.com"));
        System.out.println("searchByEmail : " + userRepository.searchByEmail("martin@fastcampos.com"));
        System.out.println("streamByEmail : " + userRepository.streamByEmail("martin@fastcampos.com"));
        System.out.println("findUserByEmail : " + userRepository.findUserByEmail("martin@fastcampos.com"));
        System.out.println("findSomethingByEmail : " + userRepository.findSomethingByEmail("martin@fastcampos.com"));

        System.out.println("findTop1ByName : " + userRepository.findTop2ByName("inderby"));
        System.out.println("findFirst1ByName : " + userRepository.findFirst1ByName("inderby"));

        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("inderby"));


        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("inderby@study.com", "inderby"));
        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("inderby@study.com", "inderby"));
        System.out.println("findByCreatedAtAfter : " + userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(4L));
        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtGreaterThanEqual : " + userRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtBetween : " + userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L)));
        System.out.println("findByIdBetween : " + userRepository.findByIdBetween(1L, 3L));
        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : " + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L));
        System.out.println("findByIdIsNotNull : " + userRepository.findByIdIsNotNull());
        //System.out.println("findByAddressIsNotEmpty : " + userRepository.findByAddressIsNotEmpty());
        System.out.println("findByNameIn : " + userRepository.findByNameIn(Lists.newArrayList("martin", "dennis")));
        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("in"));
        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("in"));
        System.out.println("findByNameContains : " + userRepository.findByNameContains("in"));
        System.out.println("findByNameLike : " + userRepository.findByNameLike("%" + "art"+ "%")); //%를 통해 매칭의 방향을 정할 수 있음
    }

    @Test
    void pagingAndSortingTest(){
        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("inderby"));
        System.out.println("findTop1ByName : " + userRepository.findTop1ByNameOrderByIdDesc("inderby"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc :" + userRepository.findFirstByNameOrderByIdDescEmailAsc("inderby"));

        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("inderby",getSort()));
        System.out.println("findByNameWithPaging : " + userRepository.findByName("inderby", PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getTotalElements());

    }

    private Sort getSort(){
        return Sort.by(Sort.Order.desc("id")
                , Sort.Order.asc("email")
                , Sort.Order.desc("createdAt"),
                Sort.Order.desc("updatedAt"));
    }

    @Test
    void insertAndUpdateTest(){
        User user = new User();

        user.setName("martin");
        user.setEmail("martin2@fastcampus.com");

        userRepository.save(user);//insert case

        User user1 = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        user1.setName("marrrtin");

        userRepository.save(user1);//update case
    }
    @Test
    void enumTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        System.out.println(userRepository.findByRawRecord().get("gender"));
    }

    @Test
    void listenerTest(){
        User user = new User();
        user.setEmail("martin@study.com");
        user.setName("martin");

        userRepository.save(user);

        User user1 = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        user1.setName("marrrtin");

        userRepository.save(user1);

        userRepository.deleteById(4L);
    }

    @Test
    void prePersistTest(){
        User user = new User();
        user.setEmail("inderby@aba.com");
        user.setName("inderby");
    //    user.setCreatedAt(LocalDateTime.now());
    //    user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        System.out.println(userRepository.findByEmail("inderby@aba.com"));
    }

    @Test
    void preUpdateTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        System.out.println("as-is : " + user);

        user.setName("martin22");

        userRepository.save(user);
        System.out.println("to-be : " + userRepository.findAll().get(0));
    }

    @Test
    void userHistoryTest(){

        User user = new User();
        user.setEmail("inderby@naver.com");
        user.setName("inderby-new");
        userRepository.save(user);

        user.setName("inderby-new-new");

        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
    }
    @Test
    void userRelationTest(){
        User user = new User();
        user.setName("david");
        user.setEmail("david@study.com");
        user.setGender(Gender.MALE);

        userRepository.save(user);
        user.setName("danial");
        userRepository.save(user);
        user.setEmail("danial@study.com");
        userRepository.save(user);
        //userHistoryRepository.findAll().forEach(System.out::println);

        //List<UserHistory> result = userHistoryRepository.findByUserId(userRepository.findByEmail("danial@study.com").getId());
        List<UserHistory> result = userRepository.findByEmail("danial@study.com").getUserHistories();
        result.forEach(System.out::println);

        System.out.println("UserHistory.getUser() : " + userHistoryRepository.findAll().get(0).getUser());
    }

    @Test
    void embedTest(){
        userRepository.findAll().forEach(System.out::println);

        User user = new User();
        user.setName("steve");
        user.setAddress(new Address("서울시", "강남구", "강남대로 3534", "04542" ));

        userRepository.save(user);
        
        userRepository.findAll().forEach(System.out::println);
    }
}