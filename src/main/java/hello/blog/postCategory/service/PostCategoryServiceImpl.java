package hello.blog.postCategory.service;

import hello.blog.postCategory.domain.PostCategory;
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

    //  카테고리 Id 기준 해당되는 게시글 조회
    @Override
    public List<PostCategory> CategoryByIdGetPost(Long categoryId) {
        return postCategoryRepository.findByCategoryId(categoryId);
    }
}
