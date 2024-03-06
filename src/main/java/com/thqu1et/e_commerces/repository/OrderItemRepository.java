package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
