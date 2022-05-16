package com.salle.server.domain.dto;

import com.salle.server.domain.entity.Category;
import com.salle.server.domain.entity.ProductComment;
import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private ProductStatus status;
    private String title;
    private Long price;
    private String description;
    private User user;
    private Category category;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private LocalDateTime deletedTime;
    private Long likesCount;
    private boolean likes;
    private List<ProductComment> productComments;

    public void setLikesTrueIfHistoryExists(Long productLikeId) {
        if (productLikeId.equals(id)) {
            this.likes = true;
        }
    }
}
