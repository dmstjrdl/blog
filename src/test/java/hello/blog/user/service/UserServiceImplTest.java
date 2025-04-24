package hello.blog.user.service;

import hello.blog.user.domain.User;
import hello.blog.user.dto.LoginDto;
import hello.blog.user.dto.RegisterDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void register() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("test");
        registerDto.setPassword("123456");
        registerDto.setEmail("test@test.com");

        User user = userService.register(registerDto);
        Assertions.assertThat(user.getUsername()).isEqualTo("test");
    }

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("123456");
    }
}