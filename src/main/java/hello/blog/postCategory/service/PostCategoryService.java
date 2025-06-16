package hello.blog.postCategory.service;

import hello.blog.postCategory.domain.PostCategory;

import java.util.List;

public interface PostCategoryService {
    List<PostCategory> findAll();
    List<PostCategory> CategoryByIdGetPost(Long categoryId);
}
