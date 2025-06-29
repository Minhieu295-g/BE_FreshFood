package com.freshfood.repository;

import com.freshfood.model.ProductImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImportRepository extends JpaRepository<ProductImport, Integer> {
    @Query(value = """
    SELECT 
        pv.id AS id,
        pv.name AS name,
        COALESCE(SUM(pi.quantity), 0) AS total_imported,
        COALESCE(SUM(oi.quantity) FILTER (
            WHERE o.order_status = 'DELIVERED'
        ), 0) AS total_sold,
        COALESCE(SUM(pi.quantity), 0) -
        COALESCE(SUM(oi.quantity) FILTER (
            WHERE o.order_status = 'DELIVERED'
        ), 0) AS stock_remaining
    FROM product_variants pv
    LEFT JOIN product_imports pi ON pi.product_variant_id = pv.id
    LEFT JOIN order_items oi ON oi.product_variant_id = pv.id
    LEFT JOIN orders o ON o.id = oi.order_id
    GROUP BY pv.id, pv.name
    ORDER BY pv.id
    """, nativeQuery = true)
    List<Object[]> getProductInventoryRaw();

}
