package com.salle.server.domain.dto;

import java.util.ArrayList;

public class ParentCategoryDTO extends ProductCategoryComponent {

  private ArrayList<ProductCategoryComponent> productCategoryComponents;

  public ParentCategoryDTO(long id, String name) {
    super(id, name);
    this.productCategoryComponents = new ArrayList<>();
  }

  @Override
  public void addProductCategory(ProductCategoryComponent productCategoryComponent) {
    productCategoryComponents.add(productCategoryComponent);
  }

  @Override
  public void removeProductCategory(ProductCategoryComponent productCategoryComponent) {
    for (ProductCategoryComponent p : productCategoryComponents) {
      if (p.getId() == productCategoryComponent.getId()) {
        productCategoryComponents.remove(p);
        break;
      }
    }
    System.out.println("상품이 없습니다.");
  }

  @Override
  public int getPrice() {
    int price = 0;
    for (ProductCategoryComponent p : productCategoryComponents) {
      price += p.getPrice();
    }
    return price;
  }

  @Override
  public int getCount() {
    int count = 0;
    for (ProductCategoryComponent p : productCategoryComponents) {
      count += p.getCount();
    }
    return count;
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
