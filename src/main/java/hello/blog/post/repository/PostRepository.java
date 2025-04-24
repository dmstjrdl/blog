package hello.blog.post.repository;

import hello.blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingIgnoreCase(String title);
    List<Post> findTop6ByOrderByIdDesc();
    List<Post> findByUserId(Long userId);
}
