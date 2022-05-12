package com.salle.server.repository;

import com.salle.server.config.QueryDSLConfig;
import com.salle.server.domain.dto.ProductDTO;
import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductComment;
import com.salle.server.domain.entity.ProductNestedComment;
import com.salle.server.domain.enumeration.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
@Import(QueryDSLConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCommentRepository productCommentRepository;
    @Autowired
    private ProductNestedCommentRepository productNestedCommentRepository;
    @Autowired
    private EntityManager entityManager;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("Product Save 테스트")
    void productSaveTest() {
        Product product = new Product();
        product.setStatus(ProductStatus.SELL);
        productRepository.save(product);
    }

    @Test
    @DisplayName("Product lazy fetch 테스트")
    void productLazyFetchTest() {
        productRepository.findById(1L);
    }

    @Test
    @DisplayName("Product eager fetch 테스트")
    void productEagerFetchTest() {
        List<Product> allProducts = productRepository.findAll();
        ProductComment productComment = ProductComment.getRawInstance(allProducts.get(0), null);
        productComment.setContent("잘 읽고 갑니다.");
        productCommentRepository.save(productComment);
        List<ProductDTO> productDto =
                allProducts.stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
        assertThat(productDto.get(0).getProductComments().get(0).getContent()).contains("잘 읽고 갑니다.");
    }

    @Test
    @DisplayName("ProductComment cascade 테스트")
    void productCommentCascadeTest() {
        Product product = productRepository.findAll().get(0);

        ProductComment productComment = ProductComment.getRawInstance(product, null);
        productComment.setContent("잘 읽고 갑니다.");
        productCommentRepository.save(productComment);
        entityManager.flush();

        ProductNestedComment productNestedComment = ProductNestedComment.getInstance(product, null);
        ProductComment newProductComment = productCommentRepository.findAll().get(0);
        newProductComment.addProductNestedComment(productNestedComment);
        productCommentRepository.save(newProductComment);

        productCommentRepository.delete(productComment);
        entityManager.flush();
    }
}