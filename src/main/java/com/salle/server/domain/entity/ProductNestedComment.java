package com.salle.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_NESTED_COMMENT")
public class ProductNestedComment {

    public static ProductNestedComment getInstance(Product product, User user) {
        return new ProductNestedComment(product, user);
    }

    private ProductNestedComment(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductComment productComment;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setDeletedTime(LocalDateTime deletedTime) {
        this.deletedTime = deletedTime;
    }

    public void setUpdatedAndDeletedTime() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedTime = now;
        this.deletedTime = now;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateProductComment(ProductNestedComment requestProductNestedComment) {
        this.updatedTime = LocalDateTime.now();
        this.content = requestProductNestedComment.getContent();
    }

    public void setProductComment(ProductComment productComment) {
        this.productComment = productComment;
    }
}
