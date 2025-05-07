package com.freshfood.service.impl;

import com.freshfood.dto.request.ReviewRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ReviewImageResponseDTO;
import com.freshfood.dto.response.ReviewReplyResponseDTO;
import com.freshfood.dto.response.ReviewResponseDTO;
import com.freshfood.model.Product;
import com.freshfood.model.Review;
import com.freshfood.model.ReviewImage;
import com.freshfood.repository.ReviewRepository;
import com.freshfood.service.ProductService;
import com.freshfood.service.ReviewService;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductService productService;
    @Override
    public int addReview(ReviewRequestDTO reviewRequestDTO, String[] imageUrl) {
        Review review = Review.builder()
                .user(userService.findByUserId(reviewRequestDTO.getUserId()))
                .product(productService.getProduct(reviewRequestDTO.getProductId()))
                .comment(reviewRequestDTO.getComment())
                .isVerified(true)
                .rating(reviewRequestDTO.getRating())
                .build();
        review.setReviewImages(convertToReviewImage(imageUrl, review));
        reviewRepository.save(review);
        return review.getId();
    }

    @Override
    public Review getReview(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public PageResponse getReviewsByProductId(int productId, int pageNo, int pageSize) {
        Product product = productService.getProduct(productId);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Review> reviews = reviewRepository.findByProduct(product, pageable);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream().map(review -> ReviewResponseDTO.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .productId(review.getProduct().getId())
                .username(review.getUser().getUsername())
                .fullName(review.getUser().getFullName())
                .comment(review.getComment())
                .rating(review.getRating())
                .date(review.getCreatedAt())
                .reply((review.getReviewReply() != null) ?
                        ReviewReplyResponseDTO.builder()
                                .id(review.getReviewReply().getId())
                                .username(review.getReviewReply().getAdmin().getUsername())
                                .fullName(review.getReviewReply().getAdmin().getFullName())
                                .date(review.getReviewReply().getCreatedAt())
                                .reply(review.getReviewReply().getReplyText())
                                .build()
                        : null
                )
                .images(review.getReviewImages().stream().map(reviewImage -> ReviewImageResponseDTO.builder()
                        .id(reviewImage.getId())
                        .imageUrl(reviewImage.getImageUrl())
                        .build()).collect(Collectors.toCollection(HashSet::new)))
                .build()).toList();

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(reviews.getTotalPages())
                .items(reviewResponseDTOS)
                .build();
    }

    private HashSet<ReviewImage> convertToReviewImage(String[] urlImage, Review review) {
        HashSet<ReviewImage> reviewImages = new HashSet<>();
        for (int i = 0; i < urlImage.length; i++) {
            reviewImages.add(ReviewImage.builder()
                    .imageUrl(urlImage[i])
                    .review(review)
                    .build());

        }
        return reviewImages;
    }
}
