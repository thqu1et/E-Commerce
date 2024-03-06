package com.thqu1et.e_commerces.service;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId , Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public List<Product> findAllProducts();

    public Page<Product> getAllProduct(String category, List<String> colors , List<String> sizes ,
                                       Integer minPrice , Integer maxPrice , Integer minDiscount ,
                                       String sort, String stock , Integer pageNumber , Integer pageSize);

}
