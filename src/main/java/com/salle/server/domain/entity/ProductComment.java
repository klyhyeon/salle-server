package com.salle.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_COMMENT")
public class ProductComment {

    public static ProductComment getRawInstance(Product product, User user) {
        return new ProductComment(product, user);
    }

    public static ProductComment getNestedInstance(Product product, User user, ProductComment nestedComment) {
        return new ProductComment(product, user, nestedComment);
    }

    private ProductComment(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    private ProductComment(Product product, User user, ProductComment nestedComment) {
        this.product = product;
        this.user = user;
        this.nestedComment = nestedComment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToOne
    @JoinColumn(name = "nested_comment")
    private ProductComment nestedComment;

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

    public void updateProductComment(ProductComment requestProductComment) {
        this.updatedTime = LocalDateTime.now();
        this.content = requestProductComment.getContent();
    }
}
