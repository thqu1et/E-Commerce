package com.thqu1et.e_commerces.service.Implementation;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.model.Rating;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.RatingRepository;
import com.thqu1et.e_commerces.request.RatingRequest;
import com.thqu1et.e_commerces.service.ProductService;
import com.thqu1et.e_commerces.service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProdutsRating(Long productId) {
        return ratingRepository.getAllProductRating(productId);
    }
}
