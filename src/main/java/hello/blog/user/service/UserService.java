package hello.blog.user.service;

import hello.blog.CustomUserDetails;
import hello.blog.user.domain.User;
import hello.blog.user.dto.RegisterDto;

public interface UserService {

    User register(RegisterDto registerDto);

    User findById(Long userId);
    boolean update(CustomUserDetails customUserDetails, User user, String newPassword);
    void delete(Long userId);
}
