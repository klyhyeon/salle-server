package com.salle.server.domain.dto;

public class CategoryDTO extends ProductCategoryComponent {

  private long id;
  private String name;

  public CategoryDTO(long id, String name) {
    super(id, name);
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
    return 0;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }
}
