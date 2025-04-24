package hello.blog.post.domain;

import hello.blog.category.domain.Category;
import hello.blog.postCategory.PostCategory;
import hello.blog.comment.domain.Comment;
import hello.blog.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    private String title;
    private String content;
    private int views;

    private String thumbnail;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostCategory> categories = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikePost> likes = new ArrayList<>();

    public void addCategory(Category category) {
        PostCategory postCategory = new PostCategory();
        postCategory.setPost(this);
        postCategory.setCategory(category);
        this.categories.add(postCategory);
    }
}
