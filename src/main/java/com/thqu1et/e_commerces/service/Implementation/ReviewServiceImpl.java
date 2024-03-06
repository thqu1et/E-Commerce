package com.thqu1et.e_commerces.service.Implementation;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.model.Review;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.ProductRepository;
import com.thqu1et.e_commerces.repository.ReviewRepository;
import com.thqu1et.e_commerces.request.ReviewRequest;
import com.thqu1et.e_commerces.service.ProductService;
import com.thqu1et.e_commerces.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());
        review.setReview(req.getReview());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllreview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
