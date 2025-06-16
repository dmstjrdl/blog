package hello.blog.comment.controller;

import hello.blog.CustomUserDetails;
import hello.blog.comment.domain.Comment;
import hello.blog.comment.service.CommentService;
import hello.blog.post.domain.Post;
import hello.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    //  댓글 등록
    @PostMapping("/add/{postId}")
    public String addComment(@PathVariable Long postId, @ModelAttribute("content") String content, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        commentService.writingComments(content, postId, customUserDetails);

        return "redirect:/post/" + postId;
    }

    //  댓글 삭제
    @GetMapping("/delete")
    public String deleteComment(@RequestParam Long commentId, @RequestParam Long postId) {
        commentService.deletingComments(commentId);
        return "redirect:/post/" + postId;
    }

    //  댓글 수정 폼
    @GetMapping("/edit")
    public String editCommentForm(@RequestParam Long commentId, @RequestParam Long postId, Model model) {
        Comment findComment = commentService.getCommentById(commentId);
        Post findPost = postService.postById(postId);
        model.addAttribute("post", findPost);
        model.addAttribute("comment", findComment);
        return "commentEditForm";
    }

    //  댓글 수정
    @PostMapping("/edit")
    public String editComment(@ModelAttribute Comment comment) {
        commentService.updatingComments(comment.getId(), comment.getContent());
        return "redirect:/post/" + comment.getPost().getId();
    }
}
