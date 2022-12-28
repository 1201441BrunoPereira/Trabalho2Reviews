package com.RecoveryReviewC.RecoveryReviewC.services;

import com.RecoveryReviewC.RecoveryReviewC.model.Product;
import com.RecoveryReviewC.RecoveryReviewC.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(String product){
        Product pt = Product.readJson(product);
        productRepository.save(pt);
    }

    public String getProducts(String message) throws JsonProcessingException {
        String pageNumber = message.substring(6);
        int page = Integer.parseInt(pageNumber);
        List<Product> productList = productRepository.getAllByPage(PageRequest.of(page,10));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productList);
        System.out.println(" Products:  " + json);
        return json;
    }
}
