package com.RecoveryReviewC.RecoveryReviewC.repository;

import com.RecoveryReviewC.RecoveryReviewC.model.Product;
import com.RecoveryReviewC.RecoveryReviewC.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query("SELECT p FROM Product p")
    List<Product> getAllProducts();

    @Query("SELECT f FROM Product f")
    List<Product> getAllByPage(Pageable pageable);

}
