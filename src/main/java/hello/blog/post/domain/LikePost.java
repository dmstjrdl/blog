package hello.blog.post.domain;

import hello.blog.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class LikePost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKEPOST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
