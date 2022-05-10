package com.salle.server.domain.entity;

import com.salle.server.domain.enumeration.ProductStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id; //자동생성
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; //판매중(0), 판매완료(1), 삭제(2),

    private String title;

    @Embedded
    private Address address;
    private int price;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public long getHoursFromUpload() {
        long now = System.currentTimeMillis();
        long diffTime = now - createdTime.toInstant(ZoneOffset.of("+09:00")).toEpochMilli();
        long hours = diffTime / (1000 * 3600);
        if (hours < 1) {
            hours = 0;
        }
        return hours;
    }

    public ProductStatus getStatus() {
        return status;
    }
}
