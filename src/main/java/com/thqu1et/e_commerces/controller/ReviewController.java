package com.thqu1et.e_commerces.controller;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.Review;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.request.ReviewRequest;
import com.thqu1et.e_commerces.service.ReviewService;
import com.thqu1et.e_commerces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReviewReview(@RequestBody ReviewRequest req,
                                                     @RequestHeader("Authorization") String jwt) throws UserException , ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req , user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/ product/{productld}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productld) throws UserException, ProductException{
        List<Review> reviews = reviewService.getAllreview(productld);
        return new ResponseEntity<>(reviews , HttpStatus.CREATED);
    }
}
