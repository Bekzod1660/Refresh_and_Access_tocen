package com.example.my_jwt.controller;

import com.example.my_jwt.dto.ApiResponse;
import com.example.my_jwt.entity.Product;
import com.example.my_jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/product/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('ADD')")

    @PostMapping("add")
    public ApiResponse add(
            @RequestBody Map<String, String> newProduct
    ) {
        Product product = Product.builder()
                .name(newProduct.get("name"))
                .info(newProduct.get("info")).build();
        Product save = productRepository.save(product);
        return new ApiResponse(
                0,
                "Bekzod",
                save
        );
    }
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ')")
    @GetMapping("list")
    public ApiResponse getProductList(){
        return new ApiResponse(
                55,
                "Bekzod",
                productRepository.findAll()
        );
    }
}
