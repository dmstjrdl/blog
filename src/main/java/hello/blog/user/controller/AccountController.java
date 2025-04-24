package hello.blog.user.controller;

import hello.blog.user.dto.LoginDto;
import hello.blog.user.dto.RegisterDto;
import hello.blog.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;


    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto registerDto) {
        userService.register(registerDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        boolean login = userService.login(loginDto, request, response);
        if (!login) {
            return "login";
        }

        return "redirect:/";
    }

}
