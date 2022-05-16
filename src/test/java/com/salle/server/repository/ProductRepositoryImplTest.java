package com.salle.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salle.server.config.QueryDSLConfig;
import com.salle.server.domain.entity.*;
import com.salle.server.domain.enumeration.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDSLConfig.class)
@ActiveProfiles("dev")
class ProductRepositoryImplTest {


    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCommentRepository productCommentRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setStatus(ProductStatus.SELL);
        product.setUser(null);
        Product savedProduct = productRepository.save(product);

        ProductComment productComment = new ProductComment();
        productComment.setContent("test");
        savedProduct.addProductComment(productComment);
        productCommentRepository.save(productComment);

        ProductComment productComment1 = new ProductComment();
        productComment1.setContent("test1");
        savedProduct.addProductComment(productComment1);
        productCommentRepository.save(productComment1);

        entityManager.flush();
        entityManager.close();
    }

    @Test
    @DisplayName("QueryDSL read 테스트")
    void queryDslCrudTest() {
        QProduct qProduct = QProduct.product;
        List<Product> allProducts = jpaQueryFactory.selectFrom(qProduct).fetch();
        assertThat(allProducts.size()).isGreaterThan(0);
    }


    @Test
    @DisplayName("product read 테스트")
    void productRead() {
        Product product = productRepository.findAll().get(0);
        product.getId();
    }

    @Test
    @DisplayName("querydsl fetch join 테스트")
    void querydslFetchJoin() {
        QProduct qProduct = QProduct.product;
        QProductComment qProductComment = QProductComment.productComment;
        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .leftJoin(qProduct.productComments, qProductComment)
                .fetchJoin()
                .fetch();
        assertThat(products.get(0).getProductComments().size()).isGreaterThan(1);
    }
}