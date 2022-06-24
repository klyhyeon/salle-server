package com.salle.server.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParentCategoryDTOTest {


  @Test
  @DisplayName("ParentCategoryDTO 테스트")
  void parentCategoryDTO() {
    ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO(1, "ManCategory");
    CategoryDTO categoryDTO = new CategoryDTO(11, "ManShoes");
    parentCategoryDTO.addProductCategory(categoryDTO);
  }

}