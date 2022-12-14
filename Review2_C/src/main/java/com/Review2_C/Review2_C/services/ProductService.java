package com.Review2_C.Review2_C.services;

import com.Review2_C.Review2_C.Interfaces.repository.ProductRepository;
import com.Review2_C.Review2_C.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(String product){
        Product pt = Product.readJson(product);
        productRepository.save(pt);
    }

    public void updateDataBaseProduct(String product) {
        try{
            JSONArray array = new JSONArray(product);

            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);

                ObjectMapper objectMapper = new ObjectMapper();
                Product pt = objectMapper.readValue(jsonObject1.toString(), Product.class);
                System.out.println("PT: " + pt.getSku());
                productRepository.save(pt);
            }

        }catch(Exception e) {
            System.out.println("Error in Result as " + e.toString());
        }
    }
}
