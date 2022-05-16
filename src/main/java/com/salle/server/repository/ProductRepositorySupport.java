package com.salle.server.repository;

import com.salle.server.domain.dto.ProductDTO;
import com.salle.server.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositorySupport {

    List<Product> findByTitleOrDescription(String searchWord, String searchWordNoSpace);

    Page<ProductDTO> findAllWithComments(Pageable pageable);
}
