package com.salle.server.repository;

import com.salle.server.domain.entity.Product;

import java.util.List;

public interface ProductRepositorySupport {

    List<Product> findByTitleOrDescription(String searchWord, String searchWordNoSpace);
}
