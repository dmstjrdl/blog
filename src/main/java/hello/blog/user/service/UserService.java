package hello.blog.user.service;

import hello.blog.CustomUserDetails;
import hello.blog.user.domain.User;
import hello.blog.user.dto.LoginDto;
import hello.blog.user.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    User register(RegisterDto registerDto);
    boolean login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response);

    User profile(Long userId);
    boolean update(CustomUserDetails customUserDetails, User user, String newPassword);
    void delete(Long userId);
}
