package com.practice.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Review>(reviewService.createReview(payload.get("reviewBody"),payload.get("imdbId")), HttpStatus.CREATED);
    }

    @PutMapping("/change/{objectId}")
    public ResponseEntity<Review> updateReview(@PathVariable ObjectId objectId, @RequestBody String reviewBody){
        Optional<Review> reviewData = reviewRepository.findById(objectId);

        if(reviewData.isPresent()){
            Review _review = reviewData.get();
            _review.setBody(reviewBody);
            return new ResponseEntity<>(reviewRepository.save(_review),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
