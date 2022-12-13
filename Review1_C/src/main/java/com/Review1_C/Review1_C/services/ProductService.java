package com.Review1_C.Review1_C.services;

import com.Review1_C.Review1_C.Interfaces.repository.ProductRepository;
import com.Review1_C.Review1_C.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(String product){
        ProductDTO pt = ProductDTO.readJson(product);
        productRepository.save(pt);
    }
}
