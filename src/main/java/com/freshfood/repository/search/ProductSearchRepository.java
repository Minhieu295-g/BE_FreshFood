package com.freshfood.repository.search;

import com.freshfood.dto.response.DefaultProduct;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Slf4j
public class ProductSearchRepository {
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;
    public ProductSearchRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public PageResponse<?> getAllDefaultProductsWithSortAndSearch(int pageNo, int pageSize, String sort, String search) {
        StringBuilder sqlQuery = new StringBuilder("""
        SELECT new com.freshfood.dto.response.DefaultProduct(
            p.id,
            p.name,
            p.thumbnailUrl,
            pv.price,
            pv.discountPercentage
        )
        FROM Product p
        JOIN ProductVariant pv ON pv.product = p
        WHERE pv.price = (
            SELECT MIN(pv2.price) FROM ProductVariant pv2 WHERE pv2.product = p
        )
    """);

        if (StringUtils.hasLength(search)) {
            sqlQuery.append("""
            AND (LOWER(p.name) LIKE LOWER(:search)
            OR LOWER(p.unit) LIKE LOWER(:search))
        """);
        }

        // 🔃 Thêm sắp xếp
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?):(asc|desc)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY p.%s %s", matcher.group(1), matcher.group(2).toUpperCase()));
            }
        }

        Query query = entityManager.createQuery(sqlQuery.toString(), DefaultProduct.class);

        if (StringUtils.hasLength(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        // 📄 Phân trang
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<DefaultProduct> products = query.getResultList();

        // 🧮 Đếm tổng số bản ghi
        Query countQuery = entityManager.createQuery("""
        SELECT COUNT(p)
        FROM Product p
        JOIN ProductVariant pv ON pv.product = p
        WHERE pv.price = (
            SELECT MIN(pv2.price) FROM ProductVariant pv2 WHERE pv2.product = p
        )
    """ + (StringUtils.hasLength(search) ? " AND (LOWER(p.name) LIKE LOWER(:search) OR LOWER(p.unit) LIKE LOWER(:search))" : ""));

        if (StringUtils.hasLength(search)) {
            countQuery.setParameter("search", "%" + search + "%");
        }

        long totalItems = (long) countQuery.getSingleResult();
        int totalPage = (int) Math.ceil((double) totalItems / pageSize);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .items(products)
                .totalPage(totalPage)
                .build();
    }

}
