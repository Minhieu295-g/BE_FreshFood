package com.freshfood.repository;

import com.freshfood.model.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Integer> {
}
