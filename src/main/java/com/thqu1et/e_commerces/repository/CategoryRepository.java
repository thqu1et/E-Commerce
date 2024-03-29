package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category , Long> {

    public Category findByName(String name);
    @Query(value = "Select c from Category c where c.name = :name and c.parentCategory.name = :parentCategoryName")
    public  Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
