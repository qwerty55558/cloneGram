package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.JpaCommentRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final JpaCommentRepository commentRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/comment")
    public String commentReceive(@RequestParam(name = "comment") String comment, @RequestParam(name = "postId") Long postId){
        String usernameOnSession = userService.getUsernameOnSession();
        Optional<User> byName = userRepository.findByName(usernameOnSession);
        if (byName.isPresent()){
            Comment commentForm = new Comment();
            commentForm.setContent(comment);
            commentForm.setPostId(postId);
            commentForm.setUserId(byName.get().getId());
            commentRepository.save(commentForm);
        }
        return "redirect:/post?id=" + postId;
    }

}
