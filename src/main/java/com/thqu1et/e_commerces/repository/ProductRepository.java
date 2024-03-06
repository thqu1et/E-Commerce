package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query(value = "SELECT p FROM Product p" +
//            "WHERE (p.category.name = :category OR :category = '')" +
//            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))" +
//            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount)"+
//            "ORDER BY" +
//            "CASE WHEN :sort = 'price_lov' THEN p.discountedPRrice END ASC," +
//            "CASE WHEN :sort = 'price_high' THEN p.discountedPRrice END DESC"
//    )
    @Query(value = "SELECT p FROM Product p " +
            "WHERE (p.category.name = :category OR :category = '') " +
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " + // Исправлено 'price_lov' на 'price_low'
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC" // Исправлено 'price_lov' на 'price_high'
    )
    public List<Product> filterProducts(@Param("category")String category ,
                                        @Param("minPrice")Integer minPrice,
                                        @Param("maxPrice")Integer maxPrice,
                                        @Param("minDiscount")Integer minDiscount,
                                        @Param("sort")String sort);


}
