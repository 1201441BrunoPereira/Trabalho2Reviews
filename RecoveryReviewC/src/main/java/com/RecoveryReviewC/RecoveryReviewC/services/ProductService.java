package com.RecoveryReviewC.RecoveryReviewC.services;

import com.RecoveryReviewC.RecoveryReviewC.model.Product;
import com.RecoveryReviewC.RecoveryReviewC.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(String product){
        Product pt = Product.readJson(product);
        productRepository.save(pt);
    }
}
