package com.freshfood.repository.search;

import com.freshfood.dto.response.*;
import com.freshfood.model.Category;
import com.freshfood.model.Product;
import com.freshfood.model.ProductVariant;
import com.freshfood.repository.ProductRepository;
import com.freshfood.repository.specification.SpecSearchCriteria;
import com.freshfood.repository.specification.SpecificationsBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.freshfood.repository.specification.SearchOperation.LIKE;
import static com.freshfood.util.AppConst.SEARCH_SPEC_OPERATOR;

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

        Query countQuery = entityManager.createQuery("""
        SELECT COUNT(p)
        FROM Product p
        JOIN ProductVariant pv ON pv.product = p
        WHERE pv.price = (
            SELECT MIN(pv2.price) FROM ProductVariant pv2 WHERE pv2.product = p
        )
    """ + (StringUtils.hasLength(search) ? " AND (LOWER(p.name) LIKE LOWER(:search) ) OR LIKE LOWER(:search))" : ""));

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

    public PageResponse<?> advanceSearchWithSpecification(Pageable pageable, String[] product, String[] category, String[] productVariant){
        if(productVariant != null){
            return getProductJoinCategoryAndJoinProductVariant(pageable, product, category, productVariant);
        }
        else if(category != null){
            return getProductJoinCategory(pageable, product, category);
        }else if(product !=null){
            SpecificationsBuilder builder = new SpecificationsBuilder();
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for(String p : product){
                Matcher matcher = pattern.matcher(p);
                if(matcher.find()){
                    builder.with(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                }
            }
            List productList = productRepository.findAll(builder.buildProduct());
            return PageResponse.builder()
                    .pageNo(pageable.getPageNumber())
                    .pageSize(pageable.getPageSize())
                    .items(convertProductResponseDTO(productList.stream().toList()))
                    .totalPage(10)
                    .build();
        }
        Page<Product> productList = productRepository.findAll(pageable);
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .items(convertProductResponseDTO(productList.stream().toList()))
                .totalPage(10)
                .build();
    }

    private PageResponse<?> getProductJoinCategoryAndJoinProductVariant(Pageable pageable, String[] product, String[] category, String[] productVariant) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        Join<Category, Product> categoryJoin = root.join("category");
        Join<ProductVariant, Product> productVariantProductJoin = root.join("productVariants");

        List<Predicate> productPredicates = new ArrayList<>();
        List<Predicate> categoryPredicates = new ArrayList<>();
        List<Predicate> productVariantPredicates = new ArrayList<>();
        if(product!=null){
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String p : product){
                Matcher matcher = pattern.matcher(p);
                if(matcher.find()){
                    SpecSearchCriteria specSearchPd = new SpecSearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    Predicate predicate = toPredicate(root, builder, specSearchPd);
                    productPredicates.add(predicate);
                }
            }
        }
        if(category !=null){
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String c : category){
                Matcher matcher = pattern.matcher(c);
                if(matcher.find()){
                    SpecSearchCriteria specSearchCate = new SpecSearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    Predicate predicate = toPredicateCategoryJoin(categoryJoin, builder, specSearchCate);
                    categoryPredicates.add(predicate);
                }
            }
        }
        if(productVariant!=null){
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String p : productVariant){
                Matcher matcher = pattern.matcher(p);
                if(matcher.find()){
                    SpecSearchCriteria specSearchCate = new SpecSearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    Predicate predicate = toPredicateProductVariantJoin(productVariantProductJoin, builder, specSearchCate);
                    productVariantPredicates.add(predicate);
                }
            }
        }
        Predicate productPredicate = builder.or(productPredicates.toArray(new Predicate[0]));
        Predicate categoryPredicate = builder.or(categoryPredicates.toArray(new Predicate[0]));
        Predicate variantPredicate = builder.and(productVariantPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate;
        if(category==null && product==null && productVariant !=null) finalPredicate = variantPredicate;
        else if(category == null && product!=null && productVariant!=null){
            finalPredicate = builder.and(productPredicate,variantPredicate);
        }else if(category !=null && product == null && productVariant!=null){
            finalPredicate = builder.and(categoryPredicate,variantPredicate);
            log.info("Da vao day ne category voi variant");
        }else{
            finalPredicate = builder.and(productPredicate,categoryPredicate,variantPredicate);
        }
        query.where(finalPredicate);
        List<Product> productList = entityManager.createQuery(query).setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPage(10)
                .items(convertProductResponseDTO(productList.stream().toList()))
                .build();
    }

    private PageResponse<?> getProductJoinCategory(Pageable pageable, String[] product, String[] category) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        Join<Category, Product> categoryJoin = root.join("category");

        List<Predicate> productPredicates = new ArrayList<>();
        List<Predicate> categoryPredicates = new ArrayList<>();
        if(product!=null){
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String p : product){
                Matcher matcher = pattern.matcher(p);
                if(matcher.find()){
                    SpecSearchCriteria specSearchPd = new SpecSearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    Predicate predicate = toPredicate(root, builder, specSearchPd);
                    productPredicates.add(predicate);
                }
            }
        }
        if(category !=null){
            Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
            for (String c : category){
                Matcher matcher = pattern.matcher(c);
                if(matcher.find()){
                    SpecSearchCriteria specSearchCate = new SpecSearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    Predicate predicate = toPredicateCategoryJoin(categoryJoin, builder, specSearchCate);
                    categoryPredicates.add(predicate);
                }
            }

        }
        Predicate productPredicate = builder.or(productPredicates.toArray(new Predicate[0]));
        Predicate categoryPredicate = builder.or(categoryPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate;
        if(category!=null && product==null) finalPredicate = categoryPredicate;
        else finalPredicate = builder.and(productPredicate, categoryPredicate);
        query.where(finalPredicate);
        List<Product> productList = entityManager.createQuery(query).setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPage(10)
                .items(convertProductResponseDTO(productList.stream().toList()))
                .build();
    }
    public Predicate toPredicate(Root<Product> root, CriteriaBuilder builder, SpecSearchCriteria criteria) {
        log.info(criteria.getValue().toString() + " day la no");
        Path<?> path = root.get(criteria.getKey());

        if (criteria.getOperation() == LIKE && path.getJavaType() != String.class) {
            return builder.equal(path, criteria.getValue());
        }

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(path, criteria.getValue());
            case NEGATION -> builder.notEqual(path, criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE -> builder.like(path.as(String.class), "%" + criteria.getValue().toString() + "%");
            case STARTS_WITH -> builder.like(path.as(String.class), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(path.as(String.class), "%" + criteria.getValue());
            case CONTAINS -> builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
        };
    }
    public Predicate toPredicateCategoryJoin(Join<Category, Product> root, CriteriaBuilder builder, SpecSearchCriteria criteria) {
        log.info(criteria.getValue().toString() + " day la no");
        log.info(criteria.toString() + "day ne");
        Path<?> path = root.get(criteria.getKey());
        log.info(path.toString() + "path o day");
        log.info(criteria.toString() + "day ne");
        if (criteria.getOperation() == LIKE && path.getJavaType() != String.class) {
            log.info(path.toString() + "path o day");
            return builder.equal(path, criteria.getValue());
        }

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(path, criteria.getValue());
            case NEGATION -> builder.notEqual(path, criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE -> builder.like(path.as(String.class), "%" + criteria.getValue().toString() + "%");
            case STARTS_WITH -> builder.like(path.as(String.class), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(path.as(String.class), "%" + criteria.getValue());
            case CONTAINS -> builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
        };
    }
    public Predicate toPredicateProductVariantJoin(Join<ProductVariant, Product> root, CriteriaBuilder builder, SpecSearchCriteria criteria) {
        log.info(criteria.getValue().toString() + " day la no");
        log.info(criteria.toString() + "day ne");
        Path<?> path = root.get(criteria.getKey());
        log.info(path.toString() + "path o day");
        log.info(criteria.toString() + "day ne");
        if (criteria.getOperation() == LIKE && path.getJavaType() != String.class) {
            log.info(path.toString() + "path o day");
            return builder.equal(path, criteria.getValue());
        }

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(path, criteria.getValue());
            case NEGATION -> builder.notEqual(path, criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE -> builder.like(path.as(String.class), "%" + criteria.getValue().toString() + "%");
            case STARTS_WITH -> builder.like(path.as(String.class), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(path.as(String.class), "%" + criteria.getValue());
            case CONTAINS -> builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
        };
    }
    private List<ProductResponseDTO> convertProductResponseDTO(List<Product> products){
        List<ProductResponseDTO> productResponseDTOS = products.stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .category(CategoryResponseDTO.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .productImages((HashSet<ProductImageResponseDTO>) product.getProductImages().stream().map(image -> ProductImageResponseDTO.builder()
                        .id(image.getId())
                        .altText(image.getAltText())
                        .imageUrl(image.getImageUrl())
                        .build()).collect(Collectors.toSet()))
                .productVariants((HashSet<ProductVariantResponseDTO>) product.getProductVariants().stream().map(variant -> ProductVariantResponseDTO.builder()
                        .id(variant.getId())
                        .name(variant.getName())
                        .price(variant.getPrice())
                        .unit(variant.getUnit().toString())
                        .expiryDate(variant.getExpiryDate())
                        .status(variant.getStatus().toString())
                        .discountPercentage(variant.getDiscountPercentage())
                        .thumbnailUrl(variant.getThumbnailUrl())
                        .build()).collect(Collectors.toSet()))
                .build()).toList();
        return productResponseDTOS;
    }

}
