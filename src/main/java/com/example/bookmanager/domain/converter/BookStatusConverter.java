package com.example.bookmanager.domain.converter;

import com.example.bookmanager.repository.dto.BookStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

@Converter(autoApply = true)//converter를 자주사용할 경우 유용한 옵션
public class BookStatusConverter implements AttributeConverter<BookStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        return attribute.getCode();//null로 구현했을 때 persistence context에 의해 자동 update가 실행되어 실제 데이터가 유실 될 수 있기 때문에 매우매우 주의
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? new BookStatus(dbData) : null;
    }
}
