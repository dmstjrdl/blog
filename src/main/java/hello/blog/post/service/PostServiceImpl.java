package hello.blog.post.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.blog.category.domain.Category;
import hello.blog.category.service.CategoryService;
import hello.blog.post.dto.AddPostDto;
import hello.blog.post.repository.PostRepository;
import hello.blog.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CategoryService categoryService;
    private final PostRepository postRepository;

    //  썸네일 경로
    @Value("${file.dir}")
    private String fileDir;

    //  게시글 등록
    @Override
    public void addPost(AddPostDto addPostDto) {
        Post post = new Post();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<String> categoryNames = objectMapper.readValue(addPostDto.getCategories(), new TypeReference<List<String>>() {});
            for (String name : categoryNames) {
                String trimmedName = name.trim();
                if (trimmedName.isEmpty()) {
                    continue;
                }
                Category category = categoryService.findOrCreateCategory(trimmedName);

                post.addCategory(category);
            }

            if (!addPostDto.getThumbnail().isEmpty()) {
                String fullName = fileDir + addPostDto.getThumbnail().getOriginalFilename();
                addPostDto.getThumbnail().transferTo(new File(fullName));
                post.setThumbnail("/file/" + addPostDto.getThumbnail().getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        post.setTitle(addPostDto.getTitle());
        post.setContent(addPostDto.getContent());
        post.setUser(addPostDto.getUser());

        postRepository.save(post);
    }

    //  게시글 조회
    @Override
    public Post postById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));
    }

    //  게시글 전부 조회 (Pageable) 추가 예정
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    //  게시글 제목 조회
    @Override
    public List<Post> postByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    //  가장 최신 6개의 게시글 조회
    @Override
    public List<Post> getTop6Posts() {
        return postRepository.findTop6ByOrderByIdDesc();
    }

    //  특정 사용자가 작성한 게시글 조회
    @Override
    public List<Post> postByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    //  게시글 수정
    @Override
    public void updatePost(Long postId, Post post) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());

        postRepository.save(findPost);
    }

    //  게시글 삭제
    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
