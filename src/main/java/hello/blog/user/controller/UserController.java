package hello.blog.user.controller;

import hello.blog.CustomUserDetails;
import hello.blog.comment.domain.Comment;
import hello.blog.comment.service.CommentService;
import hello.blog.post.domain.Post;
import hello.blog.post.service.PostService;
import hello.blog.user.domain.User;
import hello.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/user")
    public String profile(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = customUserDetails.getUser();
        List<Post> posts = postService.postByUserId(user.getId());
        List<Comment> comments = commentService.getCommentsByUserId(user.getId());

        int totalViews = posts.stream().mapToInt(Post::getViews).sum();
        int totalComments = comments.size();

        model.addAttribute("user", user);
        model.addAttribute("totalViews", totalViews);
        model.addAttribute("totalComments", totalComments);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);

        return "userProfile";
    }

    @GetMapping("/user/edit")
    public String editProfilePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = customUserDetails.getUser();

        model.addAttribute("user", user);
        return "userEditProfile";
    }

    @PostMapping("/user/edit")
    public String editProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute User user, @ModelAttribute("newPassword") String newPassword) {
        boolean result = userService.update(customUserDetails, user, newPassword);
        if (result) {
            return "redirect:/user";
        } else {
            return "redirect:/user/edit";
        }
    }
}
