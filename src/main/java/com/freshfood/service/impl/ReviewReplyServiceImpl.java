package com.freshfood.service.impl;

import com.freshfood.dto.request.ReviewReplyRequestDTO;
import com.freshfood.model.ReviewReply;
import com.freshfood.repository.ReviewReplyRepository;
import com.freshfood.service.ReviewReplyService;
import com.freshfood.service.ReviewService;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewReplyServiceImpl implements ReviewReplyService {
    private final ReviewReplyRepository reviewReplyRepository;
    private final UserService userService;
    private final ReviewService reviewService;
    @Override
    public int addReviewReply(ReviewReplyRequestDTO reviewReplyRequestDTO) {
        ReviewReply reviewReply = ReviewReply.builder()
                .replyText(reviewReplyRequestDTO.getReplyText())
                .review(reviewService.getReview(reviewReplyRequestDTO.getReviewId()))
                .admin(userService.findByUserId(reviewReplyRequestDTO.getUserId()))
                .build();
        reviewReplyRepository.save(reviewReply);
        return reviewReply.getId();
    }

    @Override
    public void updateReviewReply(int id, ReviewReplyRequestDTO reviewReplyRequestDTO) {
        ReviewReply reviewReply = reviewReplyRepository.findById(id).orElse(null);
        reviewReply.setReplyText(reviewReplyRequestDTO.getReplyText());
        reviewReplyRepository.save(reviewReply);
    }

    @Override
    public void deleteReviewReply(int id) {
        reviewReplyRepository.deleteById(id);
    }
}
