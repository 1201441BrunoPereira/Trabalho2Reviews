package com.Review1_C.Review1_C.Interfaces.repository;

import com.Review1_C.Review1_C.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query("SELECT r FROM Product r WHERE r.sku = :sku")
    Product getProductDTOBySku(@Param("sku") String sku);
}
