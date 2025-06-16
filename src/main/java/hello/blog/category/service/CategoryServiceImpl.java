package hello.blog.category.service;

import hello.blog.category.domain.Category;
import hello.blog.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    //  카테고리 조회 혹은 등록
    @Override
    public Category findOrCreateCategory(String name) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            return existingCategory.get();
        } else {
            Category category = new Category();
            category.setName(name);
            return categoryRepository.save(category);
        }
    }

    //  모든 카테고리 조회
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findById(Long id) {
        return categoryRepository.findById(id).map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    public Category findByName(String name) {
        return null;
    }
}
