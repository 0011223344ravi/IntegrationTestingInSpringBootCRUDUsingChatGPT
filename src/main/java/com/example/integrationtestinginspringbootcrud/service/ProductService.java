package com.example.integrationtestinginspringbootcrud.service;



import com.example.integrationtestinginspringbootcrud.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void saveProduct(Product product);

    void deleteProduct(Long id);
}

