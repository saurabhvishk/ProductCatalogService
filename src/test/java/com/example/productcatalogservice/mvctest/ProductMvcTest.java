package com.example.productcatalogservice.mvctest;

import com.example.productcatalogservice.controllers.ProductController;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProductAPI_StatusOlny() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());

    }
    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus() throws Exception {

        //Arrange
        Product product1 = new Product();
        product1.setName("Iphone12");
        Product product2 = new Product();
        product2.setName("Iphone118");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productList)));
    }

    @Test
    public void Test_PostProductsAPI_TestsContentAndStatus() throws Exception {

        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Iphone12");
        Product product = new Product();
        product.setName("Iphone12");
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act and Assert
        mockMvc.perform(post("/products")
                    .content(objectMapper.writeValueAsString(productDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    public void Test_PostProductsAPI_TestsContentAndJSON() throws Exception {

        //Arrange

        ProductDto productDto = new ProductDto();
        productDto.setId(12L);
        productDto.setName("Macbook");
        productDto.setPrice(2000D);

        Product product = new Product();
        product.setId(12L);
        product.setName("Macbook");
        product.setPrice(2000D);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.name").value("Macbook"))
                .andExpect(jsonPath("$.price").value(2000D));
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndJSON() throws Exception {

        //Arrange
        Product product1 = new Product();
        product1.setName("Iphone12");
        Product product2 = new Product();
        product2.setName("Iphone18");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productList)))
                .andExpect(jsonPath("$.[0].name").value("Iphone12"))
                .andExpect(jsonPath("$.[1].name").value("Iphone18"))
                .andExpect(jsonPath("$.length()").value(2));

    }
}
