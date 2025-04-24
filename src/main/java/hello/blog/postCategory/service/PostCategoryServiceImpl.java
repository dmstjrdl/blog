package hello.blog.postCategory.service;

import hello.blog.postCategory.PostCategory;
import hello.blog.postCategory.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    @Override
    public List<PostCategory> findAll() {
        return postCategoryRepository.findAll();
    }

    @Override
    public List<PostCategory> CategoryByIdGetPost(Long categoryId) {
        return postCategoryRepository.findByCategoryId(categoryId);
    }
}
