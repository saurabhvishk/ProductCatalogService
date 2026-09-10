package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    @Transactional
    public void nPlusOneProblem() {
        List<Category> categoryList = categoryRepo.findAll();
        for(Category category : categoryList) {
            if(!category.getProducts().isEmpty()) {
                List<Product> products = category.getProducts();
                for(Product product : products) {
                    System.out.println(product.getDescription());
                }
            }
        }
    }
}