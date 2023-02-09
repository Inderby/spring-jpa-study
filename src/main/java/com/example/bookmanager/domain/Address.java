package com.example.bookmanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;//시
    private String district; //구
    @Column(name = "address_detail")
    private String detail; //상세주소
    private String zipCode; //우편번호
}
