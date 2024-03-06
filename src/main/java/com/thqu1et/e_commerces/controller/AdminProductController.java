package com.thqu1et.e_commerces.controller;

import ch.qos.logback.core.model.processor.ProcessorException;
import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.request.CreateProductRequest;
import com.thqu1et.e_commerces.service.ProductService;
import com.thqu1et.e_commerces.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        Product product = productService.createProduct(req);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted Succesfully");
        res.setStatus(true);

        return new ResponseEntity<>(res , HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products , HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req , @PathVariable Long productId)throws ProductException{
        Product product = productService.updateProduct(productId , req);
        return new ResponseEntity<>(product , HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req) {
        for (CreateProductRequest product : req){
            productService.createProduct(product);
        }
        ApiResponse res = new ApiResponse();
        res.setMessage("Product created Succesfully");
        res.setStatus(true);

        return new ResponseEntity<>(res , HttpStatus.CREATED);
    }


}
