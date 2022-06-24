package com.salle.server.domain.dto;

public abstract class ProductCategoryComponent {

  final long id;
  final String name;

  public ProductCategoryComponent(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public abstract void addProductCategory(ProductCategoryComponent productCategoryComponent);

  public abstract void removeProductCategory(ProductCategoryComponent productCategoryComponent);

  public abstract int getPrice();

  public abstract int getCount();

  public abstract long getId();

  public abstract String getName();
}
