package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select c from Cart c where c.user.id = :userId")
    public Cart findByUserId(@Param("userId") Long userId);

}
