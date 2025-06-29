package com.freshfood.service;

import com.freshfood.dto.request.ClickRequestDTO;
import com.freshfood.model.Click;
import com.freshfood.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;

    public void saveClick(ClickRequestDTO clickRequestDTO){
        Click click = Click.builder()
                .userId(clickRequestDTO.getUserId())
                .productId(clickRequestDTO.getProductId())
                .build();
        clickRepository.save(click);

    }
}
