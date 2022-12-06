package com.Review2_C.Review2_C.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class ProductDTO {

    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    public ProductDTO(final String sku) {
        setSku(sku);
    }

    public ProductDTO() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
