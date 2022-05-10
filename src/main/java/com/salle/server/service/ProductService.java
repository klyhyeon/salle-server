package com.salle.server.service;

import com.salle.server.domain.entity.Category;
import com.salle.server.domain.entity.Member;
import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductImage;
import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.domain.enumeration.ProductStatus;
import com.salle.server.error.SlErrorException;
import com.salle.server.repository.ProductImageRepository;
import com.salle.server.repository.ProductRepository;
import com.salle.server.repository.ProductRepositoryImpl;
import com.salle.server.utils.AmazonS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
    private AmazonS3Service amazonS3;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          ProductRepositoryImpl productRepositoryImpl) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productRepositoryImpl = productRepositoryImpl;
    }

    @Transactional
    public void saveProductAndImages(Product product, MultipartRequest multipartRequest, Member member) {
        product.setMember(member);
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
    public void delete(int id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new SlErrorException(ErrorCode.PRODUCT_NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByCategory(Category category) {
        return productRepository.findByCategory(category);
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
    public void deleteImages(List<Integer> imageIds) {
        productImageRepository.deleteAllById(imageIds);
    }

}
