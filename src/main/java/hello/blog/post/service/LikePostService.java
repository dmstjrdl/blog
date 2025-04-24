package hello.blog.post.service;

import hello.blog.post.domain.LikePost;
import hello.blog.user.domain.User;

import java.util.List;

public interface LikePostService {
    List<LikePost> postLikes(Long postId);
    void likeAddOrDelete(Long postId, User user);
}
