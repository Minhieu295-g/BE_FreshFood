package com.freshfood.controller;

import com.freshfood.dto.request.ClickRequestDTO;
import com.freshfood.dto.response.RecommendationResponse;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.ClickService;
import com.freshfood.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendationService recommendationService;
    private final ClickService clickService;
    @GetMapping("/products")
    public ResponseData<?> getRecommendedProducts(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam int userId, @RequestParam(defaultValue = "5") int topN) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list product recommendation successfully", recommendationService.getProductRecommendations(pageNo, pageSize, userId, topN));
    }

    @PostMapping("/click")
    public ResponseData<?> saveClick(@RequestBody ClickRequestDTO clickRequestDTO) {
        clickService.saveClick(clickRequestDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Save click successfully");
    }
}
