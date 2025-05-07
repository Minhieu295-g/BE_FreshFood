package com.freshfood.service;

import com.freshfood.dto.request.ReviewRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.model.Review;
import org.springframework.data.domain.PageRequest;

public interface ReviewService {
    int addReview(ReviewRequestDTO reviewRequestDTO, String[] imageUrl);
    Review getReview(int id);
    void deleteReview(int id);
    PageResponse getReviewsByProductId(int productId, int pageNo, int pageSize);
}
