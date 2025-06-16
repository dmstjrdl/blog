package hello.blog.post.service;

import hello.blog.post.domain.LikePost;
import hello.blog.post.domain.Post;
import hello.blog.post.repository.LikePostRepository;
import hello.blog.post.repository.PostRepository;
import hello.blog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;

    @Override
    public List<LikePost> postLikes(Long postId) {
        return likePostRepository.findByPostId(postId);
    }

    //  게시글 좋아요 OR 좋아요 삭제
    @Override
    public void likeAddOrDelete(Long postId, User user) {
        LikePost findLikePost = likePostRepository.findByPostIdAndUserId(postId, user.getId());
        if (findLikePost != null) {
            likePostRepository.delete(findLikePost);

            return;
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));

        LikePost likePost = new LikePost();
        likePost.setPost(post);
        likePost.setUser(user);

        likePostRepository.save(likePost);
    }
}
