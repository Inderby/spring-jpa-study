package com.example.bookmanager.repository;

import com.example.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name); //name column에서 찾아보는 query method

    User findByEmail(String email);

    User getByEmail(String email);

    User readByEmail(String email);

    User queryByEmail(String email);

    User searchByEmail(String email);

    User streamByEmail(String email);

    User findUserByEmail(String email);

    User findSomethingByEmail(String email);
    List<User> findFirst1ByName(String name);

    List<User> findTop2ByName(String name); //2개의 record를 가져옴

    List<User> findLast1ByName(String name); //Last라는 querymethod는 존재하지 않기 때문에 무시되고 findById처럼 동작

    List<User> findByEmailAndName(String email, String name);

    List<User> findByEmailOrName(String email, String name);

    List<User> findByCreatedAtAfter(LocalDateTime yesterday);

    List<User> findByIdAfter(Long id);

    List<User> findByCreatedAtGreaterThan(LocalDateTime yesterday);
    List<User> findByCreatedAtGreaterThanEqual(LocalDateTime yesterday);

    List<User> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);
    List<User> findByIdBetween(Long id1, Long id2);

    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long id1, Long id2);

    List<User> findByIdIsNotNull();

    //List<User> findByAddressIsNotEmpty(); //collection type의 notempty를 체크함 String의 ""를 체크하는 것이 아

    List<User> findByNameIn(List<String> names);


   List<User> findByNameStartingWith(String name);
   List<User> findByNameEndingWith(String name);
   List<User> findByNameContains(String name);

   List<User> findByNameLike(String name);

   Set<User> findUserByNameIs(String name);
   Set<User> findUserByNameEquals(String name);
   Set<User> findUserByName(String name); //이 세개의 함수는 동일함 코드 가독성을 위해 어울리는 함수명을 이용하면

   List<User> findTop1ByName(String name);

   List<User> findTop1ByNameOrderByIdDesc(String name); //name으로 검색하여 Id의 역순으로 정렬했을 때 첫번째를 가져온다는 의미

    List<User> findFirstByNameOrderByIdDescEmailAsc(String name); //id의 역순, email의 정순으로 정렬하여 가져옴

    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable pageable);

    @Query(value = "select * from user limit 1;", nativeQuery = true)
    Map<String, Object> findByRawRecord();
}
