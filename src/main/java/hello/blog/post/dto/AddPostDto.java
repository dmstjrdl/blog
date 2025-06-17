package hello.blog.post.dto;

import hello.blog.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class AddPostDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String categories;

    @NotBlank(message = "본문을 입력해주세요.")
    private String content;

    private MultipartFile thumbnail;
    private User user;
}
