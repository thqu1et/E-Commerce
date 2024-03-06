package com.thqu1et.e_commerces.service;


import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Rating;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req , User user) throws ProductException;

    public List<Rating> getProdutsRating(Long productId);
}
