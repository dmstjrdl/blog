package hello.blog.user.service;

import hello.blog.CustomUserDetails;
import hello.blog.user.dto.LoginDto;
import hello.blog.user.dto.RegisterDto;
import hello.blog.user.repository.UserRepository;
import hello.blog.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public User register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return null;
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setNickname(registerDto.getNickname());
        user.setEmail(registerDto.getEmail());
        return userRepository.save(user);
    }

    @Override
    public boolean login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.getContextHolderStrategy().setContext(securityContext);

        securityContextRepository.saveContext(securityContext, request, response);

        return true;
    }

    @Override
    public User profile(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NullPointerException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public boolean update(CustomUserDetails customUserDetails, User user, String newPassword) {
        try {
            User findUser = customUserDetails.getUser();
            findUser.setNickname(user.getNickname());
            findUser.setEmail(user.getEmail());

            if (passwordEncoder.matches(user.getPassword(), findUser.getPassword()) && !passwordEncoder.matches(newPassword, findUser.getPassword())) {
                findUser.setPassword(passwordEncoder.encode(newPassword));
            }

            userRepository.save(findUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
