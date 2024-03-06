package com.thqu1et.e_commerces.service;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Review;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req , User user) throws ProductException;
    public List<Review> getAllreview(Long productId);
}
