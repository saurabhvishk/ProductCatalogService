package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @GetMapping("/products")
    public List<Product> getProducts(){
        return  null;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id){
        return null;
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product){
        return product;
    }
}
