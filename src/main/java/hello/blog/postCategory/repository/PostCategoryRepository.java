package hello.blog.postCategory.repository;

import hello.blog.postCategory.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findByCategoryId(Long categoryId);
}
