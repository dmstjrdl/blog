package hello.blog.category.controller;

import hello.blog.category.domain.Category;
import hello.blog.category.service.CategoryService;
import hello.blog.post.domain.Post;
import hello.blog.post.service.PostService;
import hello.blog.postCategory.PostCategory;
import hello.blog.postCategory.service.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final PostService postService;
    private final PostCategoryService postCategoryService;
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(@RequestParam(required = false) Long categoryId, Model model) {
        List<Post> postList = postService.getAllPosts();
        List<Category> categoryList = categoryService.findAllCategories();
        int totalPosts = postList.size();

        if (categoryId != null) {
            postList = postCategoryService.CategoryByIdGetPost(categoryId).stream()
                    .map(PostCategory::getPost)
                    .toList();
        }

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("posts", postList);
        model.addAttribute("categories", categoryList);

        return "categories";
    }
}
