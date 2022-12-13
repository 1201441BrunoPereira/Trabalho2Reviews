package com.Review2_C.Review2_C.services;


import com.Review2_C.Review2_C.Interfaces.repository.ProductRepository;
import com.Review2_C.Review2_C.model.ProductDTO;
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
