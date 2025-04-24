package hello.blog.comment.service;

import hello.blog.comment.domain.Comment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {

    void writingComments(String content, Long postId, UserDetails userDetails);
    void deletingComments(Long commentId);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentsByUserId(Long userId);
    void updatingComments(Long commentId, String content);

}
