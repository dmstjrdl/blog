package hello.blog.post.dto;

import hello.blog.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class AddPostDto {

    private String title;
    private String categories;
    private String content;
    private MultipartFile thumbnail;
    private User user;
}
