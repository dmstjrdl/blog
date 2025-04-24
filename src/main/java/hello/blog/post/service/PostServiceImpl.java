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

    @Value("${file.dir}")
    private String fileDir;

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

    @Override
    public Post postById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> postByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Post> getTop6Posts() {
        return postRepository.findTop6ByOrderByIdDesc();
    }

    @Override
    public List<Post> postByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public void updatePost(Long postId, Post post) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Not Found Post"));
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());

        postRepository.save(findPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
