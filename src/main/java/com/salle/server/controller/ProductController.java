package com.salle.server.controller;

import com.salle.server.domain.dto.ProductDTO;
import com.salle.server.service.ProductService;
import com.salle.server.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private UserService userService;


    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> allProducts = productService.getAll(pageable);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

//    @PostMapping("/shop")
//    public ResponseEntity<ProductDTO> addProduct(Product product,
//                                                    MultipartRequest multipartRequest,
//                                                    @RequestAttribute Long userId)
//    {
//        User user = userService.findUserById(userId);
//        ProductDTO newProduct = productService.save(product, multipartRequest, user);
//        return ResponseEntity.ok(newProduct);
//    }
//
//    @DeleteMapping("/shop/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//        productService.delete(id);
//        return ResponseEntity.ok("deleted");
//    }
//
//    @PutMapping("/shop")
//    public ResponseEntity<ProductDTO> updateProduct(Product product) {
//        ProductDTO updatedProduct = productService.updateProduct(product);
//        return ResponseEntity.ok(updatedProduct);
//    }
}
