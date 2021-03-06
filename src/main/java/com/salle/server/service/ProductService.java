package com.salle.server.service;

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
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          ProductRepositoryImpl productRepositoryImpl,
                          ProductLikeRepository productLikeRepository,
                          AmazonS3Service amazonS3) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productRepositoryImpl = productRepositoryImpl;
        this.productLikeRepository = productLikeRepository;
        this.amazonS3 = amazonS3;
        this.modelMapper = new ModelMapper();
    }

    @Transactional
    public ProductDTO save(Product product, MultipartRequest multipartRequest, User user) {
        product.setUser(user);
        product.setStatus(ProductStatus.SELL);
        Product savedProduct = productRepository.save(product);

        uploadAndSaveProductImages(multipartRequest, savedProduct);
        return mapToProductDTO(savedProduct);
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
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new SlErrorException(ErrorCode.PRODUCT_NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAll(Pageable pageable) {
        Page<Product> allProducts = productRepository.findAllByDeletedTimeIsNull(pageable);
        return mapToProductDTOs(allProducts);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllWithLikesAndComments(Pageable pageable, User user) {
        Page<ProductDTO> allProductsDTO = getAllWithComments(pageable);
        List<ProductLike> userProductLikeHistories = productLikeRepository.findAllByUser(user);

        userProductLikeHistories.forEach(productLike ->
                allProductsDTO.forEach(productDTO ->
                {
                    productDTO.setLikesTrueIfHistoryExists(productLike.getProduct().getId());
                }
                )
        );

        return allProductsDTO;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllWithComments(Pageable pageable) {
        productRepositoryImpl.findAllWithComments(pageable);
        return null;
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        return mapToProductDTO(findById(id));
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllByCategory(Pageable pageable, Category category) {
        return productRepository.findByCategory(pageable, category);
    }

    //TODO graphQL ??????, Product??? ????????? ???????????? ?????? ???????????? ??????, ?????? ???????????? ?????? ??????
    @Transactional(readOnly = true)
    public List<Product> findByTitleAndDescription(String searchWord, String searchWordNoSpace) {
        return productRepositoryImpl.findByTitleOrDescription(searchWord, searchWordNoSpace);
    }

    @Transactional
    public ProductDTO updateProduct(Product product) {
        Product updatedProduct = productRepository.save(product);
        return mapToProductDTO(updatedProduct);
    }

    @Transactional(readOnly = true)
    public List<String> findProductImages(Product product) {
        return productImageRepository.findByProduct(product);
    }

    @Transactional
    public void deleteImages(List<Long> imageIds) {
        productImageRepository.deleteAllById(imageIds);
    }

    public Page<ProductDTO> mapToProductDTOs(Page<Product> allProducts) {
        return allProducts.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    public ProductDTO mapToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
