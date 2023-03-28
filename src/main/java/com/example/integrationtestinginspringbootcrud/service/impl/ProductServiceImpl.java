package com.example.integrationtestinginspringbootcrud.service.impl;


import com.example.integrationtestinginspringbootcrud.entity.Product;
import com.example.integrationtestinginspringbootcrud.repo.ProductRepo;
import com.example.integrationtestinginspringbootcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> result = productRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

