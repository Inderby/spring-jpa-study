package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Book;
import com.example.bookmanager.repository.dto.BookNameAndCategory;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query(value = "update book set category='none'", nativeQuery = true)
    void update();

    List<Book> findByCategoryIsNull();

    List<Book> findAllByDeletedFalse();

    List<Book> findByCategoryIsNullAndDeletedFalse();

    List<Book> findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(String name, LocalDateTime createdAt, LocalDateTime updatedAt);

    @Query(value = "select b from Book b " +
            "where name = :name and createdAt >= :createdAt and updatedAt >= :updatedAt and category is null") //JPQL이라 부르는 쿼리 구문 대상은 entity 내에 존재하는 필드 이름
    List<Book> findByNameRecently(
            @Param("name") String name, @Param("createdAt") LocalDateTime createdAt,@Param("updatedAt") LocalDateTime updatedAt);

    @Query(value = "select new com.example.bookmanager.repository.dto.BookNameAndCategory(b.name, b.category) from Book b")
    List<BookNameAndCategory> findBookNameAndCategory();

    @Query(value = "select new com.example.bookmanager.repository.dto.BookNameAndCategory(b.name, b.category) from Book b")
    Page<BookNameAndCategory> findBookNameAndCategory(Pageable pageable);

    @Query(value = "select * from book", nativeQuery = true) //where 어노테이션이 적용되지 않음
    List<Book> findAllCustom();

    @Transactional
    @Modifying //DML(UPDATE ,DELETE)작업에서 필요한 어노테이션 update 구문이라는것을 표시
    @Query(value = "update book set category = 'it study'", nativeQuery = true) //update된 row수 반환
    int updateCategories();

    @Query(value = "show tables", nativeQuery = true)
    List<String> showTables();

    @Query(value = "select * from book order by id desc limit 1", nativeQuery = true)
    Map<String, Object> findRawRecord();
}
