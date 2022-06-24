package com.salle.server.domain.dto;

import com.salle.server.domain.entity.Category;
import com.salle.server.domain.entity.ProductComment;
import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO extends  ProductCategoryComponent {

  private long id;
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

    public ProductDTO(long id, String name) {
        super(id, name);
    }

    public void setLikesTrueIfHistoryExists(Long productLikeId) {
    if (productLikeId.equals(id)) {
      this.likes = true;
    }
  }

  @Override
  public void addProductCategory(ProductCategoryComponent productCategoryComponent) {

  }

  @Override
  public void removeProductCategory(ProductCategoryComponent productCategoryComponent) {

  }

  @Override
  public int getPrice() {
    return 0;
  }

  @Override
  public int getCount() {
    return 1;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public long getId() {
      return id;
  }

  public ProductStatus getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public User getUser() {
    return user;
  }

  public Category getCategory() {
    return category;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public LocalDateTime getModifiedTime() {
    return modifiedTime;
  }

  public LocalDateTime getDeletedTime() {
    return deletedTime;
  }

  public Long getLikesCount() {
    return likesCount;
  }

  public boolean isLikes() {
    return likes;
  }

  public List<ProductComment> getProductComments() {
    return productComments;
  }
}
