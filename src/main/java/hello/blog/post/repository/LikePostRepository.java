package hello.blog.post.repository;

import hello.blog.post.domain.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findByPostId(Long postId);
    LikePost findByPostIdAndUserId(Long postId, Long userId);
}
