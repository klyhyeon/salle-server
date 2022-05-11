package com.salle.server.domain.entity;

import com.salle.server.domain.enumeration.ProductStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; //판매중(0), 판매완료(1), 삭제(2),

    private String title;
    private Long price;
    private String description;
    //TODO camelcase convert automatically???
    private Long likesCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<ProductComment> productComments;

    public void setUser(User member) {
        this.user = member;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public long getHoursFromUpload() {
        long now = System.currentTimeMillis();
        long diffTime = now - createdTime.toInstant(ZoneOffset.of("+09:00")).toEpochMilli();
        long hours = diffTime / (1000 * 3600);
        if (hours < 1) {
            hours = 0L;
        }
        return hours;
    }

    public ProductStatus getStatus() {
        return status;
    }
}
