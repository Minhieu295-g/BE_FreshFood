package com.freshfood.service;

import com.freshfood.dto.request.ReviewReplyRequestDTO;
import com.freshfood.model.ReviewReply;

public interface ReviewReplyService {
    int addReviewReply(ReviewReplyRequestDTO reviewReplyRequestDTO);
    void updateReviewReply(int id ,ReviewReplyRequestDTO reviewReplyRequestDTO);
    void deleteReviewReply(int id);
}
