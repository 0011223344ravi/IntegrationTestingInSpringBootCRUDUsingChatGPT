package com.example.integrationtestinginspringbootcrud.repo;




import com.example.integrationtestinginspringbootcrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    // Add any custom methods you need here
}

