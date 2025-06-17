package hello.blog.user.controller;

import hello.blog.user.dto.LoginDto;
import hello.blog.user.dto.RegisterDto;
import hello.blog.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //  회원가입 화면 매핑
    @GetMapping("/register")
    public String registerForm(@ModelAttribute RegisterDto registerDto, Model model) {
        model.addAttribute("registerDto", registerDto);
        return "register";
    }

    //  회원가입 프로세스 매핑
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDto registerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "register";

        userService.register(registerDto);
        return "redirect:/login";
    }

    //  로그인 화면 매핑
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto loginDto, Model model) {
        model.addAttribute("loginDto", loginDto);
        return "login";
    }

    //  로그인 프로세스 매핑
    @PostMapping("/process")
    public String loginProcess(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            return "redirect:" + savedRequest.getRedirectUrl();
        }

        return "redirect:/";
    }

}
