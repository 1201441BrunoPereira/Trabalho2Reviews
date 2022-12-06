package com.Review1_C.Review1_C.repository;

import com.Review1_C.Review1_C.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductDTO,String> {

    @Query("SELECT r FROM ProductDTO r WHERE r.sku = :sku")
    ProductDTO getProductDTOBySku(@Param("sku") String sku);
}
