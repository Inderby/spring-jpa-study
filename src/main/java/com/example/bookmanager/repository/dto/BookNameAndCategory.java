package com.example.bookmanager.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookNameAndCategory { //또는 인터테이스로 선언하여 stream에서 메소드를 호출하는 방식으로도 tuple을 활용할 수 있다.
    private String name;
    private String category;

//    String getName();
//    String getCategory();
}
