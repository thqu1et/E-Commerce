package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "select r from Rating r where r.product.id = :productId")
    public List<Rating> getAllProductRating(@Param("productId")Long productId);

}
