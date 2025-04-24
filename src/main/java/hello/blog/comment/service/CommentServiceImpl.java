package hello.blog.comment.service;

import hello.blog.comment.domain.Comment;
import hello.blog.comment.repository.CommentRepository;
import hello.blog.post.domain.Post;
import hello.blog.post.repository.PostRepository;
import hello.blog.user.domain.User;
import hello.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void writingComments(String content, Long postId, UserDetails userDetails) {
        Comment newComment = new Comment();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));
        User user = userRepository.findByUsername(userDetails.getUsername());

        newComment.setContent(content);
        newComment.setPost(post);
        newComment.setUser(user);
        commentRepository.save(newComment);
    }

    @Override
    public void deletingComments(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("Not Found Comment"));
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public void updatingComments(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("Not Found Comment"));
        findComment.setContent(content);
        commentRepository.save(findComment);
    }
}
