package com.salle.server.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@ToString
public class Address {

    @Builder
    public Address(String region1, String region2) {
        this.region1 = region1;
        this.region2 = region2;
    }

    private String region1; //주소검색 결과

    private String region2; //상세주소

}
