package com.Review1_C.Review1_C.model;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    public Product(final String sku) {
        setSku(sku);
    }

    public Product() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public static Product readJson(String json){
        Product product = new Product();
        try{

            JSONObject object = new JSONObject(json);
            String sku = object.getString("sku");
            product.setSku(sku);

        }catch(Exception e) {
            System.out.println("Error in Result as " + e.toString());
        }

        return product;
    }
}
