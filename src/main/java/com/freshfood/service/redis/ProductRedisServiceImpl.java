package com.freshfood.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ProductResponseDTO;
import com.freshfood.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements ProductRedisService{
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private final BaseRedisService baseRedisService;
    public String getKeyFrom(Pageable pageable, String[] product, String[] category, String[] productVariant){
        StringBuilder keyBuilder = new StringBuilder();

        keyBuilder.append("page=").append(pageable.getPageNumber());
        keyBuilder.append("&size=").append(pageable.getPageSize());

        if (pageable.getSort().isSorted()) {
            String sortParam = pageable.getSort().stream()
                    .map(order -> order.getProperty() + "," + order.getDirection())
                    .collect(Collectors.joining(";"));
            keyBuilder.append("&sort=").append(encode(sortParam));
        }

        if (product != null && product.length > 0) {
            keyBuilder.append("&product=").append(encode(String.join(",", product)));
        }

        if (category != null && category.length > 0) {
            keyBuilder.append("&category=").append(encode(String.join(",", category)));
        }

        if (productVariant != null && productVariant.length > 0) {
            keyBuilder.append("&variant=").append(encode(String.join(",", productVariant)));
        }

        return keyBuilder.toString();
    }
    @Override
    public PageResponse getAllProducts(Pageable pageable, String[] product, String[] category, String[] productVariant) {
        String key = getKeyFrom(pageable, product, category, productVariant);
        String json = (String) redisTemplate.opsForValue().get(key);
        PageResponse pageResponse;
        try {
            pageResponse = json != null ? redisObjectMapper.readValue(json, new TypeReference<PageResponse<List<ProductResponseDTO>>>() {}) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return pageResponse;
    }

    @Override
    public void saveAllProducts(PageResponse<List<ProductResponseDTO>> pageResponse, Pageable pageable, String[] product, String[] category, String[] productVariant) {
        String key = getKeyFrom(pageable, product, category, productVariant);
        try {
            String json = redisObjectMapper.writeValueAsString(pageResponse);
            baseRedisService.save(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();;
    }


    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
