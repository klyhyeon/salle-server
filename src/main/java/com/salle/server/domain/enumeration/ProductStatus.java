package com.salle.server.domain.enumeration;

public enum ProductStatus {

    SELL("판매중"),
    SOLD("판매완료"),
    DELETED("삭제");

    private String status;

    ProductStatus(String status) {
        this.status = status;
    }
}
