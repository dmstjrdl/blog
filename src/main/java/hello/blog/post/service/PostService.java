package hello.blog.post.service;

import hello.blog.post.domain.Post;
import hello.blog.post.dto.AddPostDto;

import java.util.List;

public interface PostService {

    void addPost(AddPostDto addPostDto);

    Post postById(Long postId);

    List<Post> getAllPosts();
    List<Post> postByTitle(String title);
    List<Post> getTop6Posts();
    List<Post> postByUserId(Long userId);

    void updatePost(Long postId, Post post);

    void deletePost(Long postId);
}
