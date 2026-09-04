package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    public Product getProductById(Long id);
    public Product createProduct(Product product);
    public List<Product> listAllProduct();
}
