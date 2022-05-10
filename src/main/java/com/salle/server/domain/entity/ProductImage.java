package com.salle.server.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_IMAGE")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url; //AWS S3

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}