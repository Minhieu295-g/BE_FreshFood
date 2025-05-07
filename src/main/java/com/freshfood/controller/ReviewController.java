package com.freshfood.controller;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.request.ReviewRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final CloudinaryService cloudinaryService;
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> addReview(@RequestPart("images") MultipartFile[] images, @Valid @RequestPart("review")ReviewRequestDTO reviewRequestDTO) throws IOException {
        reviewService.addReview(reviewRequestDTO,cloudinaryService.uploadArrImage(images));
        return new ResponseData<>(HttpStatus.OK.value(), "Review added successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteReview(@PathVariable int id){
        reviewService.deleteReview(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete review successfully");
    }

    @GetMapping("/product/{id}")
    public ResponseData<?> getReviewsByProductId(@PathVariable int id,@RequestParam(defaultValue = "0", required = false) int pageNo,@RequestParam(defaultValue = "10", required = true)  int pageSize) throws IOException {
        return new ResponseData<>(HttpStatus.OK.value(), "Get review by product successfully", reviewService.getReviewsByProductId(id, pageNo, pageSize));
    }

}
