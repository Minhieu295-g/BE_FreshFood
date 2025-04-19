package com.freshfood.controller;

import com.freshfood.dto.request.ReviewReplyRequestDTO;
import com.freshfood.dto.request.ReviewRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.ReviewReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/review-reply")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewReplyController {
    private final ReviewReplyService reviewReplyService;

    @PostMapping("/")
    public ResponseData<?> addReviewReply(@RequestBody ReviewReplyRequestDTO reviewReplyRequestDTO){
        return new ResponseData<>(HttpStatus.OK.value(), "Review added successfully", reviewReplyService.addReviewReply(reviewReplyRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateReviewReply(@PathVariable int id,@RequestBody ReviewReplyRequestDTO reviewReplyRequestDTO){
        reviewReplyService.updateReviewReply(id, reviewReplyRequestDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Review updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteReview(@PathVariable int id){
        reviewReplyService.deleteReviewReply(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete review successfully");
    }
}
