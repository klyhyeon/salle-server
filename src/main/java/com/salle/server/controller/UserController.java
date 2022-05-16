package com.salle.server.controller;

import com.salle.server.domain.dto.ProductDTO;
import com.salle.server.domain.entity.User;
import com.salle.server.service.ProductService;
import com.salle.server.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ProductService productService;

    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/mypage/product")
    public ResponseEntity<Page<ProductDTO>> getMypageAllProducts(Pageable pageable, @RequestAttribute Long userId) {
        User user = userService.findUserById(userId);
        Page<ProductDTO> allProducts = productService.getAllWithLikesAndComments(pageable, user);
        return ResponseEntity.ok(allProducts);
    }
}
