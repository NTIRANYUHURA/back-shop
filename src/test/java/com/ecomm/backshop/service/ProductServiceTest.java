package com.ecomm.backshop.service;

import com.ecomm.backshop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;


    @Test
    public void shouldSaveProductWithSuccess(){

        Product productExpected = Product.builder()
                .productName("name test")
                .productDescription("description test")
                .productPrice(Double.valueOf("120"))
                .build();

        Product saveProduct = productService.addNewProduct(productExpected);

        Assertions.assertNotNull(saveProduct);
        Assertions.assertNotNull(saveProduct.getProductId());
        Assertions.assertEquals(productExpected.getProductName(), saveProduct.getProductName());
        Assertions.assertEquals(productExpected.getProductDescription(), saveProduct.getProductDescription());
        Assertions.assertEquals(productExpected.getProductPrice(), saveProduct.getProductPrice());


    }

    @Test
    public void shouldUpdateProductWithSuccess(){

        Product productExpected = Product.builder()
                .productName("name test")
                .productDescription("description test")
                .productPrice(Double.valueOf("120"))
                .productImages(new HashSet<>())
                .build();

        Product saveProduct = productService.addNewProduct(productExpected);

        Product productUpdate = saveProduct;
        productUpdate.setProductName("name update");

        saveProduct = productService.addNewProduct(productUpdate);

        Assertions.assertNotNull(productUpdate);
        Assertions.assertNotNull(saveProduct.getProductId());
        Assertions.assertEquals(productUpdate.getProductName(), saveProduct.getProductName());
        Assertions.assertEquals(productUpdate.getProductDescription(), saveProduct.getProductDescription());
        Assertions.assertEquals(productUpdate.getProductPrice(), saveProduct.getProductPrice());


    }





}