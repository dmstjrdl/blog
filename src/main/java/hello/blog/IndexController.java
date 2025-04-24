package hello.blog;

import hello.blog.post.service.PostService;
import hello.blog.post.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Post> posts = postService.getTop6Posts();

        for (Post post : posts) {
            log.info(post.getThumbnail());
        }

        model.addAttribute("user", userDetails);
        model.addAttribute("posts", posts);
        return "index";
    }
}
