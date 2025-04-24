package hello.blog.category.service;

import hello.blog.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category findOrCreateCategory(String name);
    List<Category> findAllCategories();
    List<Category> findById(Long id);
    Category findByName(String name);
}
