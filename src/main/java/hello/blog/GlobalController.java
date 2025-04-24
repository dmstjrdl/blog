package hello.blog;

import hello.blog.user.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute
    public void addLoginCheckAttribute(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        if (customUserDetails != null) {
            Long userId = customUserDetails.getUser().getId();
            String nickname = customUserDetails.getUser().getNickname();
            model.addAttribute("userId", userId);
            model.addAttribute("nickname", nickname);
        }
    }
}
