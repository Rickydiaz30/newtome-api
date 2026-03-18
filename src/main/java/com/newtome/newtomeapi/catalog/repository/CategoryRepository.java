package com.newtome.newtomeapi.catalog.repository;

import com.newtome.newtomeapi.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

}
