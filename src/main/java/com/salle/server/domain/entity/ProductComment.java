package com.salle.server.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_COMMENT")
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductNestedComment> productNestedComments = new ArrayList<>();

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateProductComment(ProductComment requestProductComment) {
        this.updatedTime = LocalDateTime.now();
        this.content = requestProductComment.getContent();
    }

    public void addProductNestedComment(ProductNestedComment productNestedComment) {
        productNestedComments.add(productNestedComment);
        productNestedComment.setProductComment(this);
    }
}
