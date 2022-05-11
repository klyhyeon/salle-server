package com.salle.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salle.server.domain.dto.ProductDTO;
import com.salle.server.domain.entity.*;
import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.domain.enumeration.ProductStatus;
import com.salle.server.error.SlErrorException;
import com.salle.server.repository.ProductImageRepository;
import com.salle.server.repository.ProductLikeRepository;
import com.salle.server.repository.ProductRepository;
import com.salle.server.repository.ProductRepositoryImpl;
import com.salle.server.utils.AmazonS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private ProductRepositoryImpl productRepositoryImpl;
    private ProductLikeRepository productLikeRepository;
    private AmazonS3Service amazonS3;
    private ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          ProductRepositoryImpl productRepositoryImpl,
                          ObjectMapper objectMapper,
                          ProductLikeRepository productLikeRepository,
                          AmazonS3Service amazonS3) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productRepositoryImpl = productRepositoryImpl;
        this.objectMapper = objectMapper;
        this.productLikeRepository = productLikeRepository;
        this.amazonS3 = amazonS3;
    }

    @Transactional
    public void save(Product product, MultipartRequest multipartRequest, User member) {
        product.setUser(member);
        product.setStatus(ProductStatus.SELL);
        Product savedProduct = productRepository.save(product);

        uploadAndSaveProductImages(multipartRequest, savedProduct);
    }

    public void uploadAndSaveProductImages(MultipartRequest multipartRequest, Product product) {

        List<MultipartFile> multipartFiles = convertMultipartRequestToMultipartFiles(multipartRequest);
        List<String> uploadedFileUrls = amazonS3.convertToFileAndUpload(multipartFiles);
        saveProductImages(product, uploadedFileUrls);
    }

    private void saveProductImages(Product product, List<String> uploadedFileUrls) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        for (String url : uploadedFileUrls) {
            productImage.setUrl(url);
            productImageRepository.save(productImage);
        }
    }

    private List<MultipartFile> convertMultipartRequestToMultipartFiles(MultipartRequest multipartRequest) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        Iterator<String> imageFileNames = multipartRequest.getFileNames();
        while (imageFileNames.hasNext()) {
            MultipartFile currentFile = multipartRequest.getFile(imageFileNames.next());
            multipartFiles.add(currentFile);
        }
        return multipartFiles;
    }

    @Transactional
    public void delete(Long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new SlErrorException(ErrorCode.PRODUCT_NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAll(Pageable pageable) {
        Page<Product> allProducts = productRepository.findAllByDeletedTimeIsNull(pageable);
        return mapToProductDTO(allProducts);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllWithLikesAndComments(Pageable pageable, User user) {
        Page<ProductDTO> allProductsDTO = getAll(pageable);
        List<ProductLike> userProductLikeHistories = productLikeRepository.findAllByUser(user);

        userProductLikeHistories.forEach(productLike ->
                allProductsDTO.forEach(productDTO ->
                        productDTO.setLikesTrueIfHistoryExists(productLike.getProduct().getId())
                )
        );

        return allProductsDTO;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllByCategory(Pageable pageable, Category category) {
        return productRepository.findByCategory(pageable, category);
    }

    //TODO graphQL 가능, Product는 필요한 정보들이 모든 데이터가 아닌, 부분 데이터일 때가 있음
    @Transactional(readOnly = true)
    public List<Product> findByTitleAndDescription(String searchWord, String searchWordNoSpace) {
        return productRepositoryImpl.findByTitleOrDescription(searchWord, searchWordNoSpace);
    }

    @Transactional
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<String> findProductImages(Product product) {
        return productImageRepository.findByProduct(product);
    }

    @Transactional
    public void deleteImages(List<Long> imageIds) {
        productImageRepository.deleteAllById(imageIds);
    }

    public Page<ProductDTO> mapToProductDTO(Page<Product> allProducts) {
        return allProducts.map(board -> objectMapper.convertValue(board, ProductDTO.class));
    }

}
