package com.thqu1et.e_commerces.controller;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.Rating;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.UserRepository;
import com.thqu1et.e_commerces.request.RatingRequest;
import com.thqu1et.e_commerces.service.RatingService;
import com.thqu1et.e_commerces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req,user);
        return new ResponseEntity<Rating>(rating , HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productld,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProdutsRating(productld);
        return new ResponseEntity<>(ratings , HttpStatus.CREATED);
    }
}
