package hello.blog.post.controller;

import hello.blog.CustomUserDetails;
import hello.blog.post.domain.LikePost;
import hello.blog.post.domain.Post;
import hello.blog.post.dto.AddPostDto;
import hello.blog.post.service.LikePostService;
import hello.blog.post.service.PostService;
import hello.blog.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final LikePostService likePostService;

    @GetMapping("/add")
    public String addPostForm() {
        return "postAddForm";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute AddPostDto addPostDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        addPostDto.setUser(customUserDetails.getUser());

        postService.addPost(addPostDto);
        return "redirect:/";
    }

    @GetMapping("/{postId}")
    public String postView(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Post post = postService.postById(postId);
        List<LikePost> likePosts = likePostService.postLikes(postId);
        boolean userLike = likePosts.stream().map(like -> like.getUser().getUsername().equals(userDetails.getUsername())).isParallel();


        post.setViews(post.getViews() + 1);
        postService.updatePost(postId, post);

        model.addAttribute("userLike", userLike);
        model.addAttribute("likes", likePosts);
        model.addAttribute("post", post);
        model.addAttribute("user", userDetails);
        return "postView";
    }

    @PostMapping("/like/{postId}")
    public String postLikeAdd(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        likePostService.likeAddOrDelete(postId, user);

        return "redirect:/post/" + postId;
    }

    @GetMapping("/edit")
    public String postEditForm(@RequestParam("postId") Long postId, Model model) {
        Post post = postService.postById(postId);
        model.addAttribute("post", post);
        return "postEditForm";
    }

    @PostMapping("/edit")
    public String postEdit(@RequestParam Long postId, @ModelAttribute Post post) {
        postService.updatePost(postId, post);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/delete/{postId}")
    public String postDeleteForm(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String postSearchForm(@RequestParam String title, Model model) {
        List<Post> posts = postService.postByTitle(title);
        model.addAttribute("keyword", title);
        model.addAttribute("posts", posts);
        return "postSearchView";
    }
}
