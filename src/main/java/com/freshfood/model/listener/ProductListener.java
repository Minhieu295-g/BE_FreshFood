package com.freshfood.model.listener;

import com.freshfood.model.Product;
import com.freshfood.service.redis.ProductRedisService;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ProductListener {
    private final ProductRedisService productRedisService;

    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);

    @PostUpdate
    public void postUpdated(Product product){
        productRedisService.clear();
        logger.info("Cleared Redis cache after product updated: {}", product.getId());

    }
    @PostRemove
    public void postDeleted(Product product){
        productRedisService.clear();
        logger.info("Cleared Redis cache after product deleted {}", product.getId());

    }
}
