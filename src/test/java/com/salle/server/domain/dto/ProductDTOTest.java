package com.salle.server.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

  @Test
  @DisplayName("ProductCategoryComponent Product 테스트")
  void productCategoryComponentTest() {
    ProductDTO productDTO = new ProductDTO(1, "nikeShoes");
    assertEquals("nikeShoes", productDTO.getName());
  }
}