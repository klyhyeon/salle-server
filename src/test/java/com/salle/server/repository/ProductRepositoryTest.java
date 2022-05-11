package com.salle.server.repository;

import com.salle.server.config.QueryDSLConfig;
import com.salle.server.domain.entity.Product;
import com.salle.server.domain.enumeration.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
@Import(QueryDSLConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Product Save 테스트")
    void productSaveTest() {
        Product product = new Product();
        product.setStatus(ProductStatus.SELL);
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct.getStatus()).isEqualTo(ProductStatus.SELL);
    }
}